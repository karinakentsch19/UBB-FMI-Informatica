import { IonContent, IonHeader, IonIcon, IonItem, IonLabel, IonList, IonPage, IonSearchbar } from "@ionic/react";
import { eye } from 'ionicons/icons';
import React, { useEffect, useState } from 'react';
import { useHistory } from "react-router";
import axiosInstance from "../core/axiosInstance";

interface BookProperties {
    id_book: number,
    title: string,
    author: string,
    release_date: Date,
    pages: number,
    reading_state: boolean,
    stars: number,
    image: any
}


const SearchPage: React.FC = () => {
    const history = useHistory()
    const [books, setBooks] = useState<BookProperties[]>([]);
    const [searchItem, setSearchItem] = useState("");

    useEffect(() => {
        const searchBooks = async () => {
            axiosInstance.get("/books").then((response) => {
                setBooks(response.data);
            }).catch((error) => {
                console.log(error);
            });
        }
        searchBooks();
    }, [])

    const seeFullBookDetails = (bookId: number) => {
        history.push(`/BookList/${bookId}`)
    }
    return (
        <IonPage>
            <IonHeader style={{ height: "5vh" }}>SEARCH BOOKS</IonHeader>
            <IonContent>
                <IonSearchbar
                    value={searchItem}
                    debounce={1000}
                    onIonInput={e => setSearchItem(e.detail.value!)} />
                <IonList>
                    {
                        books.filter(book => book.title.indexOf(searchItem) >= 0 || searchItem == "").map((book) => {
                            return (
                                <IonItem key={book.id_book}>
                                    <IonLabel>{book.title} by {book.author}</IonLabel>
                                    <IonIcon slot="end" icon={eye} color="001F3F" onClick={() => seeFullBookDetails(book.id_book)} />
                                </IonItem>
                            )
                        })
                    }
                </IonList>
            </IonContent>
        </IonPage>


    )
}

export default SearchPage