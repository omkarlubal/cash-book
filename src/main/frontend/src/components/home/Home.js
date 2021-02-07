import React from 'react';
import './Home.css'
import AuthService from '../../services/AuthService';
import FundService from "../../services/FundService";
import CreateTransaction from '../createTransaction/CreateTransaction';
import BalanceService from '../../services/BalanceService';

class Home extends React.Component {
    token = localStorage.getItem("hmToken");

    state = {
        username:'BOB ROSS',
        hmdrf_balance:0,
        transactions:[],
        balances:[],
    }

    fetchUsername() {
        AuthService.getUsername(this.token).then( (response) => {
            let success = response.data.success;
            if (success) {
                this.setState({username : response.data.msg})
            }
        });
    }

    fetchHMDRFBalance() {
        FundService.getHMBal(this.token).then((response) => {
            let success = response.data.success;
            if (success) {
                this.setState({hmdrf_balance : response.data.balance})
            }
        });
    }

    fetchAllTransactions() {
        FundService.getTransactions(this.token).then((response) => {
            let success = response.data.success;
            if (success) {
                this.setState({transactions: response.data.transactions})
            }
        });
    }

    fetchOutstandingBalances() {
        BalanceService.getAllAccountsDetails(this.token).then((response)=> {
            let success = response.data.success;
            if (success) {
                this.setState({balances: response.data.accounts})
            }
        })
    }

    componentDidMount() {
        this.token = localStorage.getItem("hmToken");
        this.fetchUsername();
        this.fetchHMDRFBalance();
        this.fetchAllTransactions();
        this.fetchOutstandingBalances();

    }

    
    formatDate(date) {
        let dateStr = new Date(date).toDateString();
        let timeStr = new Date(date).toLocaleTimeString();
        return timeStr+", "+dateStr;
    }


    render() {
        let name = this.state.username;
        let hm_bal = this.state.hmdrf_balance;
        return (
            <div class="row">
                {/* -------------------- LEFT COL -------------------- */}
                <div class="column" id="profile_and_hm_balance" >
                    <div class="profile" >
                        <h2>Welcome {name}!</h2>
                    </div>
                    <div class="hm_balance">
                        <h3>Current HMDRF Balance</h3>
                        <h4>Rs {hm_bal}</h4>
                    </div>
                </div>

                {/* -------------------- CENTER COL -------------------- */}
                <div  id="show_and_create_transactions">
                    <div class="transactions_header" >
                    <h2>Transactions</h2>
                    </div>   
                    <div class="transactions_scroll_view" >
                        <ul class="transactions_list">
                            {
                                this.state.transactions.reverse().map(
                                    transaction =>
                                    <li key={transaction.id}>
                                        <div className="row_txn">
                                            <div className="to_fro_div">
                                                <span class="sender">{transaction.sender}</span>
                                                <i class="fas fa-angle-double-right"></i>
                                                <span className="receiver">{transaction.recipient}</span>
                                            </div>
                                            <div className="amount_div">
                                                <i class="fas fa-wallet"></i>
                                                <span className="amount_span">Rs. <span className="amount">{transaction.amount}</span> </span>
                                            </div>
                                        </div>
                                        <i class="fas fa-comment note_icon"></i> <span className="note">{transaction.note}</span>
                                        <br></br>
                                        <i class="fas fa-calendar-day time_icon"></i>
                                        <span className="time">{this.formatDate(transaction.txnDate)}</span>
                                    </li>
                                )
                            }
                        </ul>
                    </div>
                    <div className="create_transaction_pane">
                        <h3>Transfer Funds</h3>
                        <CreateTransaction token={this.token} 
                            updateTransaction={() => this.fetchAllTransactions()}
                            updateHMBal={() => this.fetchHMDRFBalance()}
                            updateOutstanding={() => this.fetchOutstandingBalances()}
                        />
                    </div>
                </div>

                {/* -------------------- RIGHT COL -------------------- */}
                <div class="column" id="outstanding_balance" style={{backgroundColor:"green"}}>
                    <h2>Outstanding</h2>
                    <table class="outstanding_table">
                        <thead>
                            <tr>
                                <td>Name</td>
                                <td>Balance</td>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.balances.map(
                                    balance => 
                                    <tr>
                                        <td>{balance.name}</td>
                                        <td>{balance.balance}</td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>

            </div>
        )
    }
}

export default Home;