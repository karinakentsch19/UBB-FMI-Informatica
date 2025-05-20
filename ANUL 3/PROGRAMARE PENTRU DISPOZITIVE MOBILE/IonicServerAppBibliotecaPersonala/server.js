//pentru a rula server-ul
const Koa = require('koa');
const app = new Koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');
const secretKey = "856c9bf8308145bb40ae6e96e4cbee038916bb80dec123bd6bcc8d64718fb5a6abe81b0d36e06caf1fc9f4383a333bd7da6f7ea722e13c253d69f647d1367fd7352db1f92d78926939ca6fe1acd4ddfd620ccd82e4347b0b5171b3556d2a4951dcf20214704d06ff9b94bf85a435c2c6d5e3edd5d76335a086ed1ff9aa960ae85b2f0b4bf1d0a2310ff7d1696383006eda9ebf32e7e435f787205977e94ffb11c0edba1793ee005bc52dc70fbd5605696fb17affc9c4ca4032e7a0974531fbb9b6638d06f156dcf9c48f253e527f83af5f1a25b684f924ff9ef45eee691987e13c7c2daf3ef4f767020d1dfd73ebf929f2cd086f1260314e909e73aacd1b6590"
const jwt = require("jsonwebtoken");

wss.on('connection', (ws) => {

    ws.on('message', (message) => {
        const data = JSON.parse(message);
        // Handle authorization message
        if (data.type === 'authorization') {
            const {token} = data.payload;
            jwt.verify(token, secretKey, (err, decoded) => {
                if (err) {
                    console.error('JWT verification failed:', err);
                    ws.send(JSON.stringify({error: 'Unauthorized'}));
                    ws.close();
                    return;
                }

                ws.id_user = parseInt(decoded.id_user);
                console.log('User authorized:', ws.id_user);
            });
        }
    });

    ws.on('close', () => {
        console.log('Client disconnected');
    });
});

//database
const Knex = require('knex');
const config = require('./knexfile');
const {off} = require("mssql/lib/global-connection");
const knex = Knex(config.development);


app.use(bodyparser());
app.use(cors({
    origin: '*', // This allows requests from all origins, adjust this for production
    allowMethods: ['GET', 'POST', 'PUT', 'DELETE'],
    allowHeaders: ['Content-Type', 'Authorization']
}));

//printeaza ce metoda a fost utilizata, url-ul si status code-ul returnat
app.use(async (ctx, next) => {
    const start = new Date();
    await next();
    const ms = new Date() - start;
    console.log(`${ctx.method} ${ctx.url} ${ctx.response.status} - ${ms}ms`);
});

app.use(async (ctx, next) => {
    await new Promise(resolve => setTimeout(resolve, 2000));
    await next();
});

app.use(async (ctx, next) => {
    try {
        await next();
    } catch (err) {
        ctx.response.body = {message: err.message || 'Unexpected error'};
        ctx.response.status = 500;
    }
});

class Book {
    constructor({id_book, title, author, release_date, pages, reading_state, stars, image, latitudine_librarie, longitudine_librarie}) {
        this.id_book = id_book;
        this.title = title;
        this.author = author;
        this.release_date = release_date;
        this.pages = pages;
        this.reading_state = reading_state;
        this.stars = stars;
        this.image = image;
        this.latitudine_librarie = latitudine_librarie;
        this.longitudine_librarie = longitudine_librarie;

    }
}

class User {
    constructor({id_user, firstname, lastname, email, password}) {
        this.id_user = id_user;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}

const broadcast = (data, id_user) =>
    wss.clients.forEach(client => {
        if (client.readyState === WebSocket.OPEN && client.id_user === id_user) {
            client.send(JSON.stringify(data));
        }
    });

const router = new Router();

/**
 * Return all books from the database
 * @returns {Promise<void>}
 */
async function getAllBooks() {
    return knex("Books").select("*");
}

/**
 * Return all books of a user
 * @param id_user
 * @returns {Promise<Knex.QueryBuilder<TRecord, ArrayIfAlready<TResult, DeferredKeySelection.Augment<UnwrapArrayMember<TResult>, Knex.ResolveTableType<TRecord>, IncompatibleToAlt<ArrayMember<[string]>, string, never>, Knex.IntersectAliases<[string]>>>>>}
 */
async function getBooksForAUser(id_user) {
    return knex("Books")
        .join("BooksUsers", "Books.id_book", "BooksUsers.id_book")
        .join("Users", "BooksUsers.id_user", "Users.id_user")
        .where("BooksUsers.id_user", id_user)  // Modificare aici
        .select("Books.*");
}


router.get('/books', async context => {
    const authToken = context.header.authorization.split(" ").pop();
    const id_user = jwt.decode(authToken).id_user;
    console.log(id_user);
    context.response.body = await getBooksForAUser(id_user);
    context.response.status = 200;
});

/**
 * Returns a book with the given id from the database
 * @returns {Promise<void>}
 */
// async function getBookById(id_book) {
//     return knex("Books").select("*").where("id_book", id_book).first();
// }

async function getBookById(id_book) {
    const book = await knex("Books").select("*").where("id_book", id_book).first();
    if (book && book.release_date) {
        book.release_date = formatDate(book.release_date);
    }
    return book;
}


router.get('/books/:id', async (context) => {
    const bookId = context.request.params.id;
    const book = await getBookById(bookId);
    if (book) {
        context.response.body = book;
        context.response.status = 200; // ok
    } else {
        context.response.body = {message: `Book with id ${bookId} was not found`};
        context.response.status = 404; // NOT FOUND (if you know the resource was deleted, then return 410 GONE)
    }
});

async function getLastAddedBook() {
    return knex("Books").select("*").orderBy("id_book", "desc").first();
}

async function addBook(title, author, release_date, pages, reading_state, stars, id_user) {
    await knex("Books").insert({
        title: title,
        author: author,
        release_date: release_date,
        pages: pages,
        reading_state: reading_state,
        stars: stars
    });
    const book = await getLastAddedBook();
    await knex("BooksUsers").insert({id_book: book.id_book, id_user: id_user});
    return book;
}

router.post('/books/add', async (context) => {
    const {title, author, release_date, pages, reading_state, stars} = context.request.body;
    console.log(context.request.body);
    const authToken = context.header.authorization.split(" ").pop();
    const id_user = jwt.decode(authToken).id_user;
    const book = await addBook(title, author, release_date, pages, reading_state, stars, id_user);
    console.log("Cartea adaugata: ", book);
    if (book) {
        broadcast({event: 'created', payload: {book}}, id_user);
        context.response.status = 200;
    } else {
        context.response.body = {message: 'Invalid token!'}
        context.response.status = 400;
    }
});

async function updateBook(id_book, title, author, release_date, pages, reading_state, stars, image, latitudine, longitudine) {
    await knex("Books")
        .where({id_book: id_book})
        .update({
        title: title,
        author: author,
        release_date: release_date,
        pages: pages,
        reading_state: reading_state,
        stars: stars,
        image: image,
        latitudine_librarie: latitudine,
        longitudine_librarie: longitudine
    });
}

router.put('/books/update/:id_book', async (context) => {
    const {title, author, release_date, pages, reading_state, stars, image, latitudine_librarie, longitudine_librarie} = context.request.body;
    const id_book = parseInt(context.params.id_book);
    await updateBook(id_book, title, author, release_date, pages, reading_state, stars, image, latitudine_librarie, longitudine_librarie);
    context.response.status = 200;
    context.response.body = await getBookById(id_book);
});

async function getAllUsersBooksFromAPage(id_user, pageNumber, numberOfBooksOnPage) {
    const offset = (pageNumber - 1) * numberOfBooksOnPage;
    return knex("Books")
        .join("BooksUsers", "Books.id_book", "BooksUsers.id_book")
        .join("Users", "BooksUsers.id_user", "Users.id_user")
        .where("BooksUsers.id_user", id_user)  // Modificare aici
        .select("Books.*")
        .orderBy("Books.id_book")
        .offset(offset)
        .limit(numberOfBooksOnPage);
}

router.get('/booksPage/pageNumber=:pageNumber', async (context) => {
    const authToken = context.header.authorization.split(" ").pop();
    const id_user = jwt.decode(authToken).id_user;
    const pageNumber = parseInt(context.params.pageNumber);
    const books = await getAllUsersBooksFromAPage(id_user, pageNumber, 12);
    context.response.body = books;
    context.response.status = 200;
});

async function getReadBooks(id_user, reading_state) {
    return knex("Books")
        .join("BooksUsers", "Books.id_book", "BooksUsers.id_book")
        .join("Users", "BooksUsers.id_user", "Users.id_user")
        .where("BooksUsers.id_user", id_user)
        .andWhere("Books.reading_state", reading_state)
        .select("Books.*");
}

router.get('/filteredBooks/reading_state=:reading_state', async (context) => {
    const authToken = context.header.authorization.split(" ").pop();
    const id_user = jwt.decode(authToken).id_user;
    const reading_state_string = context.params.reading_state;
    const reading_state = reading_state_string === "read";
    const books = await getReadBooks(id_user, reading_state);
    context.response.body = books;
    context.response.status = 200;
});

/**
 * Returns a user with the given email from the db
 * @param email user's email
 * @returns {Promise<awaited Knex.QueryBuilder<TRecord, DeferredKeySelection.AddUnionMember<UnwrapArrayMember<TResult>, undefined>>>}
 */
async function getUserByEmail(email) {
    const user = await knex("Users").select("*").where("email", email).first();
    return user;
}

router.post('/books/login', async (context) => {
    const {email, password} = context.request.body;
    const user = await getUserByEmail(email);
    if (user && user.password === password) {
        const authToken = jwt.sign({id_user: user.id_user}, secretKey);
        context.response.body = {token: authToken};
        context.response.status = 200; // ok
    } else {
        context.response.body = {message: `Bad credentials!`};
        context.response.status = 401; // unauthorized
    }
});

router.post('/books/verifyToken', async (context) => {
    const {authToken} = context.request.body;
    try {
        jwt.verify(authToken, secretKey)
        context.response.status = 200;
    } catch (error) {
        context.response.body = {message: 'Invalid token!'}
        context.response.status = 400;
    }
});

/**
 * Returns the max book id from the database
 * @returns {Promise<void>}
 */
async function getMaxBookId() {
    const result = await knex("Books").max("id_book as maxId");
    return result[0].maxId;
}

// function formatDate(date) {
//     return new Intl.DateTimeFormat('en-GB').format(new Date(date));
// }
function formatDate(date) {
    const d = new Date(date);
    const day = String(d.getDate()).padStart(2, '0');
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const year = d.getFullYear();

    return `${day}/${month}/${year}`;
}


setInterval(async () => {
    lastUpdated = new Date();
    lastId = await getMaxBookId() + 1;
    const book = new Book({
        id_book: lastId,
        title: `Title ${lastId}`,
        author: `Author ${lastId}`,
        release_date: formatDate(Date.now()),
        pages: 0,
        reading_state: 0,
        stars: null,
        image: null
    });
    // console.log("New book: ", book);
    broadcast({event: 'created', payload: {book}}, 2);
}, 10000);

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
