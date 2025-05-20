import axios from 'axios';

const authUrl = 'http://localhost:3000/books/login';

export interface AuthProps {
    token: string;
    data?: any;
}

export const login: (email?: string, password?: string) => Promise<AuthProps> = (email, password) => {
    return axios.post(authUrl, {email, password});
}