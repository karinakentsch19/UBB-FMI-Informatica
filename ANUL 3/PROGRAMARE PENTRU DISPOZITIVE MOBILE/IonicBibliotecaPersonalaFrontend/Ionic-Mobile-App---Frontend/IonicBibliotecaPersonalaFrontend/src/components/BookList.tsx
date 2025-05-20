import { IonContent, IonItem, IonLabel, IonList, IonIcon, IonInfiniteScrollContent, IonInfiniteScroll } from "@ionic/react"
import axios from "axios";
import React, { act, useEffect, useReducer, useState } from 'react';
import { book, eye } from 'ionicons/icons';
import { useHistory } from "react-router";
import { getBooksFromAPage, newWebSocket } from "../core/apiCalls";
import axiosInstance from "../core/axiosInstance";
import { error } from "console";

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

interface ActionProperties {
    type: string,
    bookList?: BookProperties[],
    book?: BookProperties
}

function reducer(currentState: BookProperties[], action: ActionProperties): BookProperties[] {
    switch (action.type) {
        case "initializeBookList":
            if (action.bookList) {
                return action.bookList
            }
            return currentState
        case "newBookAdded":
            if (action.book) {
                return [...currentState, action.book]
            }
            return currentState
        case "loadMoreBooks":
            console.log(action.bookList);
            if (action.bookList) {
                console.log(action.bookList)
                return [...currentState, ...action.bookList]
            }
            return currentState
        default:
            return currentState
    }
}

const BookList: React.FC = () => {
    const history = useHistory()
    const [books, dispatchBooks] = useReducer(reducer, []);
    const [currentPage, setCurrentPage] = useState(1);
    const [hasMoreData, setHasMoreData] = useState(true);
    const [isLoading, setIsLoading] = useState(false);

    const loadMore = async (event: any) => {
        if (isLoading || !hasMoreData) {
            event.target.complete();
            return;
        }
        setIsLoading(true);
        try {
            const result = await getBooksFromAPage(currentPage);
            if (result.data.length > 0) {
                dispatchBooks({ type: "loadMoreBooks", bookList: result.data });
                setCurrentPage(currentPage + 1);
            }
            else {
                setHasMoreData(false);
            }
        } catch (error) {
            console.log(error);
        } finally {
            setIsLoading(false);
            event.target.complete();
        }
    }

    useEffect(() => {
        const getBooksByPage = async () => {
            try {
                const result = await getBooksFromAPage(currentPage);
                if (result.data.length > 0) {
                    dispatchBooks({ type: "initializeBookList", bookList: result.data });
                    setCurrentPage(currentPage + 1);
                }
                else {
                    setHasMoreData(false);
                }
            } catch (error) {
                console.log(error);
            }

        }
        getBooksByPage();
    }, [])

    const seeFullBookDetails = (bookId: number) => {
        history.push(`/BookList/${bookId}`)
    }

    useEffect(() => {
        const closeWebSocket = newWebSocket((data) => {
            const { event, payload } = data;
            console.log(payload)
            if (event == "created") {
                dispatchBooks({ type: "newBookAdded", book: payload.book })
            }
        }, localStorage.getItem("Authentication token"))
        return () => {
            closeWebSocket();
        };
    }, [])



    return (
        // <IonContent>
        //     <IonList>
        //         {
        //             books.map((book) => {
        //                 return (
        //                     <IonItem key={book.id_book}>
        //                         <IonLabel>{book.title} by {book.author}</IonLabel>
        //                         <IonIcon slot="end" icon={eye} color="001F3F" onClick={() => seeFullBookDetails(book.id_book)} />
        //                     </IonItem>
        //                 )
        //             })
        //         }
        //     </IonList>
        //     <IonInfiniteScroll onIonInfinite={loadMore}>
        //         <IonInfiniteScrollContent loadingSpinner="bubbles" loadingText="Loading more books..."></IonInfiniteScrollContent>
        //     </IonInfiniteScroll>
        // </IonContent>
        <IonContent style={{ height: "10%", overflowY: 'scroll' }} scroll-y={true}>
            <IonList inset={true} >
                {books.map((book) => {
                    return (
                        <IonItem key={book.id_book}>
                            <IonLabel>{book.title} by {book.author}</IonLabel>
                            <IonIcon slot="end" icon={eye} color="001F3F" onClick={() => seeFullBookDetails(book.id_book)} />
                        </IonItem>
                    );
                })}
            </IonList>
            <IonInfiniteScroll onIonInfinite={loadMore}>
                <IonInfiniteScrollContent loadingSpinner="bubbles" loadingText="Loading more books..."></IonInfiniteScrollContent>
            </IonInfiniteScroll>
        </IonContent>

    )
}

export default BookList