import axios from 'axios';
// import Constants from '../constants/Constants';

const BALANCE_ENDPOINT = '/api/balance';
const CREATE_NEW_ACCOUNT = BALANCE_ENDPOINT + '/create';
const GET_ALL_ACCOUNTS = BALANCE_ENDPOINT + '/accounts/get';
const GET_ALL_ACCOUNTS_DETAILS = BALANCE_ENDPOINT + '/accounts/getAll';

class BalanceService {

    getAllAccounts(token) {
        let headers = {
            "Authorization": "Bearer "+token,
        }
        return axios.get(GET_ALL_ACCOUNTS, {headers: headers});
    }

    getAllAccountsDetails(token) {
        let headers = {
            "Authorization": "Bearer "+token,
        }
        return axios.get(GET_ALL_ACCOUNTS_DETAILS, {headers: headers});
    }

    createNewAccount(token,account_details) {
        let headers = {
            "Authorization": "Bearer "+token,
        }
        return axios.post(CREATE_NEW_ACCOUNT, account_details,{headers: headers});
    }
}

export default new BalanceService();