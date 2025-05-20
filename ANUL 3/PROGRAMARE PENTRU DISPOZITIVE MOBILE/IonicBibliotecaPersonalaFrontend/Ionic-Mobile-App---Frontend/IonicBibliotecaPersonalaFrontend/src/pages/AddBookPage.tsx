import { createAnimation, IonAlert, IonButton, IonCheckbox, IonContent, IonDatetime, IonHeader, IonInput, IonModal, IonPage, IonTitle, IonToolbar } from '@ionic/react';
import React, { useCallback, useState } from 'react';
import axiosInstance from '../core/axiosInstance';
import { addBookApi } from '../core/apiCalls';


interface BookProperties {
    title: string,
    author: string,
    release_date: string,
    pages: number,
    reading_state: boolean,
    stars: number,
    image: any
}

const initialState: BookProperties = {
    title: '',
    author: '',
    release_date: Date.now.toString(),
    pages: 0,
    reading_state: false,
    stars: 0,
    image: null
}

interface AddBookPageProperties {
    isOpen: boolean,
    onClose: () => void
}

const enterAnimation = (baseEl: HTMLElement) => {
    const root = baseEl.shadowRoot!;

    const backdropAnimation = createAnimation()
      .addElement(root.querySelector('ion-backdrop')!)
      .fromTo('opacity', '0.01', 'var(--backdrop-opacity)');

    const wrapperAnimation = createAnimation()
      .addElement(root.querySelector('.modal-wrapper')!)
      .keyframes([
        { offset: 0, opacity: '0', transform: 'scale(0)' },
        { offset: 1, opacity: '0.99', transform: 'scale(1)' },
      ]);

    return createAnimation()
      .addElement(baseEl)
      .easing('ease-out')
      .duration(500)
      .addAnimation([backdropAnimation, wrapperAnimation]);
  };

const AddBookPage: React.FC<AddBookPageProperties> = ({isOpen, onClose}) => {
    const [book, setBook] = useState<BookProperties>(initialState);

    const addBookFunc = () => {
        addBookApi(book);
    }

    const handleChange = useCallback((label: keyof BookProperties, value: any) => {
        setBook((prevBook) => {
            if (!prevBook) return prevBook; // handle the case where book is undefined

            return {
                ...prevBook,
                [label]: value,  // Dynamically updating the correct field
            };
        });
    }, []);


    return (
        <IonModal enterAnimation={enterAnimation} isOpen={isOpen} onDidDismiss={onClose}>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>Add a book</IonTitle>
                    <IonButton slot='end' onClick={() => onClose()}>Close</IonButton>
                </IonToolbar>
            </IonHeader>


            <IonContent>
                <IonInput
                    placeholder="Title"
                    value={book.title}
                    onIonChange={(e) => handleChange("title", e.detail.value)} />


                <IonInput
                    placeholder="Author"
                    value={book.author}
                    onIonChange={(e) => handleChange("author", e.detail.value)} />

                {/* <IonDatetime presentation='date' 
                    title='Release date'
                    value={book.release_date}
                    onIonChange={(e) => handleChange("release_date", e.detail.value)} /> */}
                <IonDatetime
                    title="Release date"
                    value={book?.release_date}
                    onIonChange={(e) => {
                        const dateValue = e.detail.value;
                        if (typeof dateValue === 'string') {
                            const dateOnly = dateValue.split('T')[0];
                            handleChange("release_date", dateOnly);
                        }
                        // If dateValue is an array, handle it as per your requirements (if needed)
                    }}
                />

                <IonInput
                    placeholder="Pages"
                    value={book.pages}
                    onIonChange={(e) => handleChange("pages", e.detail.value)} />

                <IonCheckbox value={book.reading_state} labelPlacement='start' onIonChange={(e) => handleChange("reading_state", e.detail.value)}>
                    Reading state
                </IonCheckbox>

                <IonInput
                    placeholder="Stars"
                    value={book.stars}
                    onIonChange={(e) => handleChange("stars", e.detail.value)} />
                <IonButton onClick={addBookFunc}>
                    SAVE
                </IonButton>
            </IonContent>

        </IonModal>
    )
}

export default AddBookPage