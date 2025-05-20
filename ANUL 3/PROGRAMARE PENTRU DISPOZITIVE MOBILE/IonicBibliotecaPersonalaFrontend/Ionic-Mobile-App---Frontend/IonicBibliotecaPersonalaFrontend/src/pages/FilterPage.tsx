import { IonContent, IonIcon, IonItem, IonLabel, IonList, IonSelect, IonSelectOption, IonPage, IonHeader} from "@ionic/react";
import { eye } from 'ionicons/icons';
import React, { useEffect, useState } from 'react';
import { useHistory } from "react-router";
import { getReadBooks } from "../core/apiCalls";
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


const FilterPage: React.FC = () => {
    const history = useHistory()
    const [books, setBooks] = useState<BookProperties[]>([]);

    const [filterValue, setFilterValue] = useState(undefined);

    useEffect(() => {
        const filterBooks = async () => {
            if (filterValue == undefined) {
                axiosInstance.get("/books").then((response) => {
                    setBooks(response.data);
                }).catch((error) => {
                    console.log(error);
                });
            }
            else {
                const result = await getReadBooks(filterValue);
                setBooks(result.data);
            }
        }
        filterBooks();
    }, [filterValue])

    const seeFullBookDetails = (bookId: number) => {
        history.push(`/BookList/${bookId}`)
    }
    return (
        <IonPage>
            <IonHeader style={{height:"5vh"}}>FILTER BOOKS</IonHeader>
            <IonContent>
                <IonSelect value={filterValue} placeholder="Filter" onIonChange={e => setFilterValue(e.detail.value)} >
                    <IonSelectOption key={"read"} value={"read"}>Read</IonSelectOption>
                    <IonSelectOption key={"notRead"} value={"notRead"}>Not read</IonSelectOption>
                </IonSelect>
                <IonList>
                    {
                        books.map((book) => {
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

export default FilterPage