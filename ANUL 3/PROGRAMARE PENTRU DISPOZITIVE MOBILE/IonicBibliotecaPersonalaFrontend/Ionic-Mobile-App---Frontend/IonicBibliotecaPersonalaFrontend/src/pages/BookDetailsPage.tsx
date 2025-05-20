import React, { useState, useEffect, useCallback } from 'react';
import { IonAlert, IonContent, IonHeader, IonLabel, IonPage, IonTitle, IonToolbar, IonButton, IonButtons, IonInput, IonLoading, IonCard, IonCardTitle, IonCardContent, IonFab, IonFabButton, IonIcon, IonImg, createAnimation, } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import axios from "axios";
import { useParams } from 'react-router';
import axiosInstance from '../core/axiosInstance';
import "./editPage.css"
import { updateBookApi } from '../core/apiCalls';
import { usePhotos } from '../hooks/usePhotos';
import { useFiles } from '../hooks/useFiles';
import { cameraOutline } from 'ionicons/icons';
import MyMap from '../components/MyMap';
import { useMyLocation } from '../hooks/useMyLocation';
import { useMyAddress } from '../hooks/useMyAddress';


interface BookProperties {
    id_book: number,
    title: string,
    author: string,
    release_date: string,
    pages: number,
    reading_state: boolean,
    stars: number,
    image: string,
    latitudine_librarie: number,
    longitudine_librarie: number
}

function cardTitleAnimation() {
    const elements = document.querySelectorAll('.cardTitle'); 
    elements.forEach((el) => {
        const animation = createAnimation()
            .addElement(el)
            .duration(3000)
            .direction('alternate')
            .iterations(1)
            .keyframes([
                { offset: 0, opacity: '0' },
                { offset: 0.5, opacity: '0.5' },
                { offset: 1, opacity: '1' }
            ]);
        animation.play();
    });
}

const BookDetailsPage: React.FC = () => {
    const [book, setBook] = useState<BookProperties>();
    const { bookId } = useParams<{ bookId: any }>();
    const [isEditable, setIsEditable] = useState(false);
    const { takePhoto } = usePhotos();
    const { downloadImage } = useFiles();
    const myLocation = useMyLocation();
    const getAddressFromCoordinates = useMyAddress();
    const [address, setAddress] = useState("");

    useEffect(() => {
        cardTitleAnimation();
    },[book]);

    // useEffect(() => {
    //     if (book?.latitudine_librarie != null && book.longitudine_librarie != null) {
    //         getAddressFromCoordinates(book.latitudine_librarie, book.longitudine_librarie).then((data) => {
    //             if (data){
    //                 setAddress(data);
    //             }
    //         });
    //     }
    // })

    useEffect(() => {
        axiosInstance.get("/books/" + bookId)
            .then((response) => {
                setBook(response.data)
                handleChange("id_book",response.data.id_book)
                handleChange("title",response.data.title)
                handleChange("author",response.data.author)
                handleChange("release_date",response.data.release_date)
                handleChange("pages",response.data.pages)
                handleChange("reading_state",response.data.reading_state)
                handleChange("stars",response.data.stars)
                handleChange("image",response.data.image)
                if(response.data.longitudine_librarie != null){
                    console.log("cevaaaa")
                    handleChange("longitudine_librarie",response.data.longitudine_librarie)}
                if(response.data.latitudine_librarie)
                    handleChange("latitudine_librarie",response.data.latitudine_librarie)
                // if (response.data.latitudine == null || response.data.longitudine == null) {
                //     handleChange("latitudine_librarie", myLocation.position?.coords.latitude);
                //     handleChange("longitudine_librarie", myLocation.position?.coords.longitude);
                // }
            })
            .catch((error) => {
                <IonAlert isOpen={true} message="This is not a real book" />
            })
    }, [])

    //locatia curenta a laptopului - daca nu e setata
    //altfel locatia cartii e luata din baza de date
    useEffect(() => {
        if (book == null || book.latitudine_librarie == null || book.longitudine_librarie == null) {
            handleChange("latitudine_librarie", myLocation.position?.coords.latitude);
            handleChange("longitudine_librarie", myLocation.position?.coords.longitude);
        }
        console.log(book)
    },[myLocation.position])

    const handleChange = useCallback((label: keyof BookProperties, value: any) => {
        setBook((prevBook) => {
            if (!prevBook) return prevBook; // handle the case where book is undefined
            console.log("Altceva")
            console.log(prevBook)
            return {
                ...prevBook,
                [label]: value,  // Dynamically updating the correct field
            };
        });
    }, []);

    const editBook = () => {
        if (!isEditable) {
            setIsEditable(true);
        }
        else {
            if (book) {
                updateBookApi({
                    title: book.title,
                    author: book.author,
                    release_date: book.release_date,
                    pages: book.pages,
                    reading_state: book.reading_state,
                    stars: book.stars,
                    image: book.image,
                    latitudine_librarie: book.latitudine_librarie,
                    longitudine_librarie: book.longitudine_librarie
                }, bookId);
                setIsEditable(false);
            }

        }
    }

    const handleTakePhoto = () => {
        takePhoto().then((data) => {
            const imageURI = data.base64String;
            handleChange("image", imageURI);
        });
    }

    useEffect(() => {
        if (myLocation.position?.coords) {
            handleChange("latitudine_librarie", myLocation.position?.coords.latitude);
            handleChange("longitudine_librarie", myLocation.position?.coords.longitude);

        }
    }, [myLocation.position]);

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>Book page</IonTitle>
                    <IonButton slot="end" onClick={editBook}>{isEditable ? "SAVE" : "EDIT"}</IonButton>
                </IonToolbar>
            </IonHeader>
            {
                book != undefined &&
                <IonContent>
                    <IonCard className='title'>
                        <IonCardTitle className='cardTitle'>Title</IonCardTitle>
                        <IonCardContent>
                            <IonInput
                                className={!isEditable ? "disabled-input" : ""}
                                disabled={!isEditable}
                                value={book.title}
                                onIonChange={(e) => handleChange("title", e.detail.value)} />
                        </IonCardContent>
                    </IonCard>


                    <IonCard className='author'>
                        <IonCardTitle className='cardTitle'>Author</IonCardTitle>
                        <IonCardContent>
                            <IonInput
                                className={!isEditable ? "disabled-input" : ""}
                                disabled={!isEditable}
                                value={book.author}
                                onIonChange={(e) => handleChange("author", e.detail.value)} />

                        </IonCardContent>
                    </IonCard>


                    <IonCard className='release_date'>
                        <IonCardTitle className='cardTitle'>Release date</IonCardTitle>
                        <IonCardContent>
                            <IonInput
                                className={!isEditable ? "disabled-input" : ""}
                                disabled={!isEditable}
                                value={book.release_date}
                                onIonChange={(e) => handleChange("release_date", e.detail.value)} />

                        </IonCardContent>
                    </IonCard>


                    <IonCard className='pages'>
                        <IonCardTitle className='cardTitle'>Pages</IonCardTitle>
                        <IonCardContent><IonInput
                            className={!isEditable ? "disabled-input" : ""}
                            disabled={!isEditable}
                            value={book.pages}
                            onIonChange={(e) => handleChange("pages", e.detail.value)} />
                        </IonCardContent>
                    </IonCard>


                    <IonCard className='reading_state'>
                        <IonCardTitle className='cardTitle'>Reading state</IonCardTitle>
                        <IonCardContent><IonInput
                            className={!isEditable ? "disabled-input" : ""}
                            disabled={!isEditable}
                            value={book.reading_state ? "read" : "not read"}
                            onIonChange={(e) => handleChange("reading_state", e.detail.value)} />
                        </IonCardContent>
                    </IonCard>


                    <IonCard className='stars'>
                        <IonCardTitle className='cardTitle'>Stars</IonCardTitle>
                        <IonCardContent><IonInput
                            className={!isEditable ? "disabled-input" : ""}
                            disabled={!isEditable}
                            value={book.stars == null ? "no review yet" : book.stars}
                            onIonChange={(e) => handleChange("stars", e.detail.value)} /></IonCardContent>
                    </IonCard>


                    <IonCard className='location'>
                        <IonCardTitle className='cardTitle'>Location</IonCardTitle>
                        <IonLabel>{address}</IonLabel>
                        <IonCardContent>
                            {
                                book.latitudine_librarie && book.longitudine_librarie &&
                                <MyMap
                                    isEditable={isEditable}
                                    lat={book.latitudine_librarie}
                                    lng={book.longitudine_librarie}
                                    onMapClick={(location) => {
                                        if (isEditable) {
                                            handleChange("latitudine_librarie", location.latitude);
                                            handleChange("longitudine_librarie", location.longitude);
                                        }
                                    }}
                                    onMarkerClick={(location) => {
                                        if (isEditable) {
                                            handleChange("latitudine_librarie", location.latitude);
                                            handleChange("longitudine_librarie", location.longitude);
                                        }
                                    }}
                                />
                            }
                        </IonCardContent>

                    </IonCard>

                    <IonCard className='image'>
                        <IonCardTitle className='cardTitle'>Image</IonCardTitle>
                        <IonCardContent>
                            {book.image && (
                                <IonImg
                                    src={`data:image/jpeg;base64,${book.image}`}
                                    alt="Captured"
                                    style={{
                                        width: '25%',
                                        height: 'auto'
                                    }}
                                    onClick={() => downloadImage(`data:image/jpeg;base64,${book.image}`)}
                                />
                            )}
                        </IonCardContent>
                    </IonCard>

                    {
                        isEditable && (
                            <IonFab vertical="bottom" horizontal="end">
                                <IonFabButton onClick={handleTakePhoto} >
                                    <IonIcon icon={cameraOutline} />
                                </IonFabButton>
                            </IonFab>)
                    }



                </IonContent>
            }
        </IonPage>
    )
}

export default BookDetailsPage