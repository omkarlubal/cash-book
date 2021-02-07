import axios from 'axios';
// import Constants from '../constants/Constants';

const PING_URL = '/auth/ping';
const AUTHENTICATE_USER = '/auth/authenticate';
const FETCH_USERNAME = '/auth/username/get';
const VALIDATE_USER_SESSION = '/auth/validate';

class AuthService {

    ping() {
        return axios.get(PING_URL);
    }

    authenticate(credentials) {
        return axios.post(AUTHENTICATE_USER,credentials);
    }

    getUsername(token) {
        let headers = {
            "Authorization": "Bearer "+token,
        }
        return axios.post(FETCH_USERNAME,{},{headers: headers});
    }

    checkTokenValid(token) {
        let headers = {
            "Authorization": "Bearer "+token,
        }
        return axios.get(VALIDATE_USER_SESSION,{headers: headers});
    }

}

export default new AuthService();