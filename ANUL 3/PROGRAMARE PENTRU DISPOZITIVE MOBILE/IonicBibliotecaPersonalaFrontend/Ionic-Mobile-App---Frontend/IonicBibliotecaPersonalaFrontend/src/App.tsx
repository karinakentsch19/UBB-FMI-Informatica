import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import { IonApp, IonRouterOutlet, setupIonicReact } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import Home from './pages/Home';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';
import BookDetailsPage from './pages/BookDetailsPage';
import { AuthProvider } from './authentication/AuthProvider';
import { Login } from './pages/Login';
import { PrivateRoute } from './authentication/PrivateRoute';
import AddBookPage from './pages/AddBookPage';
import FilterPage from './pages/FilterPage';
import SearchPage from './pages/SearchPage';

setupIonicReact();

const App: React.FC = () => (
  <IonApp>
    <IonReactRouter>
      <IonRouterOutlet>
        <AuthProvider>
          <Route path="/login" component={Login} exact={true} />
          <PrivateRoute path="/BookList" component={Home} exact={true} />
          <PrivateRoute path='/BookList/:bookId' component={BookDetailsPage} exact={true} />
          {/* <PrivateRoute path='/BookAdd' component={AddBookPage} exact={true} /> */}
          <PrivateRoute path='/FilteredBooks' component={FilterPage} exact={true}/> 
          <PrivateRoute path='/SearchBooks' component={SearchPage} exact={true}/> 
          <Route exact path="/" render={() => <Redirect to="/BookList" />} />
        </AuthProvider>
      </IonRouterOutlet>
    </IonReactRouter>
  </IonApp>
);

export default App;
