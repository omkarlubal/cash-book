import axios from 'axios';
// import Constants from '../constants/Constants';

const FUND_ENDPOINT = '/api/funds';
const GET_HM_BAL = FUND_ENDPOINT + '/hmbal/get';
const GET_TRANSACTIONS = FUND_ENDPOINT + '/transactions/getAll';
const TRANSFER_FUNDS = FUND_ENDPOINT + '/transfer'

class FundService {
    getHMBal(token) {
        let headers = {
            "Authorization": "Bearer "+token,
        }
        return axios.get(GET_HM_BAL,{headers: headers});
    }

    getTransactions(token) {
        let headers = {
            "Authorization": "Bearer "+token,
        }
        return axios.get(GET_TRANSACTIONS,{headers: headers});
    }

    transferFunds(token, body) {
        let headers = {
            "Authorization": "Bearer "+token,
        }
        return axios.post(TRANSFER_FUNDS,body,{headers: headers});
    }
}

export default new FundService();