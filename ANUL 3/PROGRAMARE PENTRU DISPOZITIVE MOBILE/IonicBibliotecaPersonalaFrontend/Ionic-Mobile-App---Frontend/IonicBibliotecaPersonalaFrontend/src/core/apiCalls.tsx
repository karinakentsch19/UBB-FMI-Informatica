import { read } from "fs";
import axiosInstance from "./axiosInstance";

const baseUrl = `ws://localhost:3000`;

export const newWebSocket = (onMessageReceived: (data: any) => void, token: string | null) => {
  const ws = new WebSocket(baseUrl)
  ws.onopen = () => {
    ws.send(JSON.stringify({ type: 'authorization', payload: { token } }));
  };
  ws.onclose = () => { console.log("web socket closed") };
  ws.onerror = error => { console.error(error) };
  ws.onmessage = messageEvent => {
    console.log(messageEvent.data)
    onMessageReceived(JSON.parse(messageEvent.data));
  };
  return () => {
    ws.close();
  }
}

interface AddBookProperties {
  title: string,
  author: string,
  release_date: string,
  reading_state: boolean,
  pages: number,
  stars: number,
  image?: string,
  latitudine_librarie?: number,
  longitudine_librarie?: number
}


export const addBookApi = (book: AddBookProperties) => {
  if (window.navigator.onLine == false) {
    alert(`Book ${book.title} by ${book.author} was not saved!!`)
    const createdBooks = localStorage.getItem('created');
    if (!createdBooks) { // nu exista sectiunea created
      localStorage.setItem('created', JSON.stringify([book]));
    } else { // exista sectiunea created
      const books = JSON.parse(createdBooks);
      localStorage.setItem('created', JSON.stringify([...books, book]));
    }
  }
  else {
    axiosInstance
      .post("/books/add", book)
      .then((response) => {
        // Book successfully added
      })
      .catch((error) => {
        if (error.message === "Network Error") { // Server offline
          alert(`Book ${book.title} by ${book.author} was not saved!!`)
          const createdBooks = localStorage.getItem('created');
          if (!createdBooks) { // nu exista sectiunea created
            localStorage.setItem('created', JSON.stringify([book]));
          } else { // exista sectiunea created
            const books = JSON.parse(createdBooks);
            localStorage.setItem('created', JSON.stringify([...books, book]));
          }
        }
      });
  }
};

export const getBooksFromAPage = (page: number) => {
  return axiosInstance.get(`/booksPage/pageNumber=${page}`);
}

export const getReadBooks = (reading_state: string) => {
  return axiosInstance.get(`/filteredBooks/reading_state=${reading_state}`);
}

export const updateBookApi = (book: AddBookProperties, id_book: number) => {
  if (window.navigator.onLine == false) {
    alert(`Book ${book.title} by ${book.author} was not updated!!`)
    const updatedBooks = localStorage.getItem('updated');
    if (!updatedBooks) { // nu exista sectiunea created
      localStorage.setItem('updated', JSON.stringify([book]));
    } else { // exista sectiunea created
      const books = JSON.parse(updatedBooks);
      localStorage.setItem('updated', JSON.stringify([...books, book]));
    }
  }
  else {
    axiosInstance
      .put(`/books/update/${id_book}`, book)
      .then((response) => {
        // Book successfully added
      })
      .catch((error) => {
        if (error.message === "Network Error") { // Server offline
          alert(`Book ${book.title} by ${book.author} was not updated!!`)
          const updatedBooks = localStorage.getItem('updated');
          if (!updatedBooks) { // nu exista sectiunea created
            localStorage.setItem('updated', JSON.stringify([book]));
          } else { // exista sectiunea created
            const books = JSON.parse(updatedBooks);
            localStorage.setItem('updated', JSON.stringify([...books, book]));
          }
        }
      });
  }
};