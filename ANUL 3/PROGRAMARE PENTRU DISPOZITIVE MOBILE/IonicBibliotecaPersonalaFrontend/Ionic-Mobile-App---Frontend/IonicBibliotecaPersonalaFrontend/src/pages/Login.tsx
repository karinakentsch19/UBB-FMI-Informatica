import React, { useCallback, useContext, useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router';
import { IonButton, IonContent, IonHeader, IonInput, IonLoading, IonPage, IonTitle, IonToolbar } from '@ionic/react';
import { AuthContext } from '../authentication/AuthProvider';

interface LoginState {
  email?: string;
  password?: string;
}

export const Login: React.FC<RouteComponentProps> = ({ history }) => {
  const { isAuthenticated, isAuthenticating, login, authenticationError } = useContext(AuthContext);
  // const { isAuthenticated, isAuthenticating, login, authenticationError, token } = useContext(AuthContext);
  const [state, setState] = useState<LoginState>({});
  const { email, password } = state;
  const handlePasswwordChange = useCallback((e: any) => setState({
    ...state,
    password: e.detail.value || ''
  }), [state]);
  const handleUsernameChange = useCallback((e: any) => setState({
    ...state,
    email: e.detail.value || ''
  }), [state]);
  const handleLogin = useCallback(() => {
    login?.(email, password);
  }, [email, password]);
  // useEffect(() => {
  //   if (isAuthenticated) {
  //     history.push('/');
  //   }
  // }, [isAuthenticated]);
  useEffect(() => {
    if (isAuthenticated) {
      const checkTokenInLocalStorage = () => {
        const token = localStorage.getItem("Authentication token");
        if (token) {
          history.push('/'); // Only push once token is in localStorage
        }
      };

      const intervalId = setInterval(checkTokenInLocalStorage, 100); // Poll every 100ms

      return () => clearInterval(intervalId); // Clear interval on cleanup
    }
  }, [isAuthenticated]); 
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Login</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonInput
          placeholder="Email"
          value={email}
          onIonChange={handleUsernameChange}/>
        <IonInput type='password'
          placeholder="Password"
          value={password}
          onIonChange={handlePasswwordChange}/>
        <IonLoading isOpen={isAuthenticating}/>
        {authenticationError && (
          <div>{authenticationError.message || 'Failed to authenticate'}</div>
        )}
        <IonButton onClick={handleLogin}>Login</IonButton>
      </IonContent>
    </IonPage>
  );
};
