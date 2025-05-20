import React, { useContext, useEffect, useState } from 'react';
import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar, IonFab, IonFabButton, IonIcon, IonButton, IonLabel } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Home.css';
import BookList from '../components/BookList';
import { add } from 'ionicons/icons'
import { AuthContext } from '../authentication/AuthProvider';
import { log } from 'console';
import { RouteComponentProps, useHistory } from 'react-router';
import { addBookApi } from '../core/apiCalls';
import AddBookPage from './AddBookPage';


const Home: React.FC<RouteComponentProps> = ({ history }) => {
  const { isAuthenticated, isAuthenticating, login, logout, authenticationError } = useContext(AuthContext);
  const [addWindowIsOpen, setAddWindowIsOpen] = useState(false);

  const onCloseAddWindow = () => {
    setAddWindowIsOpen(false);
  } 

  const logoutFunc = () => {
    if(logout){
      logout();
    }
  }

  const[isOnline, setIsOnline] = useState(window.navigator.onLine); 
  const updateOnlineStatus = () => {
    setIsOnline(window.navigator.onLine);
  };

  const sendLocalItemsToTheServer = async () => {
    const createdAnimals = localStorage.getItem('created');
    if (createdAnimals != null) {
      const animals = JSON.parse(createdAnimals);
      localStorage.removeItem('created');
      
      await Promise.all(
        animals.map(async (book: any) => {
          await addBookApi(book);
        })
      );
      
    }
  };

  useEffect(() => {
    window.addEventListener('online', updateOnlineStatus);
    window.addEventListener('offline', updateOnlineStatus);
    sendLocalItemsToTheServer();
    return () => {
      window.removeEventListener('online', updateOnlineStatus);
      window.removeEventListener('offline', updateOnlineStatus);
    };
  }, [isOnline]); 
  
  return (
    <IonPage>
      <IonHeader className='homePageHeader'>
        <IonToolbar>
          MY BOOKS
          <IonLabel slot='end'>{isOnline? "Online":"Offline"}</IonLabel>
          <IonButton slot='end' onClick={() => history.push("/SearchBooks")}>SEARCH</IonButton>
          <IonButton slot='end' onClick={() => history.push("/FilteredBooks")}>FILTER</IonButton>
          <IonButton slot="end" onClick={logoutFunc}>LOGOUT</IonButton>
        </IonToolbar>

      </IonHeader>
      <BookList />
      <IonFab vertical="bottom" horizontal="end" slot="fixed">
        <IonFabButton color={"primary"} onClick={() => setAddWindowIsOpen(true)}>
          <IonIcon icon={add} />
        </IonFabButton>
      </IonFab>
      <AddBookPage isOpen={addWindowIsOpen} onClose={onCloseAddWindow}></AddBookPage>
    </IonPage>
  );
};

export default Home;
