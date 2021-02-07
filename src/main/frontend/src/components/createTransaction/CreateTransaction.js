import React, {useState} from 'react';
import BalanceService from '../../services/BalanceService';
import FundService from '../../services/FundService';
import {Button, Modal} from 'react-bootstrap';

import './CreateTransaction.css'

function OutsideTransactionModal(props) {
    const [show, setShow] = useState(false);
    const [name, setName] = useState('');
    const [amount, setAmount] = useState(0);
  
    const handleCreate = () => {
        let acc_details = {
            "name": name,
            "amount": amount
        }
        BalanceService.createNewAccount(props.token, acc_details).then((response) => {
            let success = response.data.success;
            if (success) {
                props.updateAccountAndOutstanding();
                setShow(false);
            } else {
                alert(response.data.msg);
            }
        });
    };

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
  
    return (
      <div className="add_new_account">
        <Button className="new_acc_button" variant="primary" onClick={handleShow}>
          Add new account
        </Button>
  
        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>Create new account</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <label>Name: </label>
            <input type="text" name="name" required onChange={(e) => setName(e.target.value)}/>
            <br></br>
            <label>Initial Amount: </label>
            <input type="text" name="amount" required onChange={(e) => setAmount(e.target.value)}/> 
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
            <Button variant="primary" onClick={handleCreate}>
              Create Account
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    );
}
  


class CreateTransaction extends React.Component {

    state = {
        accounts:[],
        sender: 'HMDRF',
        recipient: 'HMDRF',
        amount:0,
        note: ''
    }

    getAccounts() {
        BalanceService.getAllAccounts(this.props.token).then((response) => {
            let success = response.data.success;
            if (success) {
                this.setState({ accounts: response.data.accounts });
            }
        });
    }

    componentDidMount() {
        this.getAccounts();
    }

    handleChange = (e) => {
        const {name,value} = e.target;
        this.setState({
            [name]:value,
        });
    }

    handleSubmit = () => {
        let body = {
            "sender":this.state.sender,
            "recipient":this.state.recipient,
            "amount": parseFloat(this.state.amount),
            "note":this.state.note
        };
        FundService.transferFunds(this.props.token,body).then((response) => {
            let success = response.data.success;
            if (success) {
                this.props.updateTransaction();
                this.props.updateHMBal();
                this.props.updateOutstanding();
                this.setState({
                    amount:0,
                    note:'',
                });
            } else {
                alert("FAILED: "+response.data.msg);
            }
        });
        console.log(body);
    }

    handleNewAccount = () => {
        this.getAccounts();
        this.props.updateOutstanding();
    }

    render () {
        return (
            <div class="transactions_create">
                <div className="row_one">
                    <div className="column_one">
                        <label>Sender </label>
                        <select name="sender" required onChange={this.handleChange}>
                            { 
                                this.state.accounts.map(
                                account => <option value={account}>{account}</option>) 
                            }
                        </select>
                        <label>Recipient </label>
                        <select name="recipient" required onChange={this.handleChange}>
                            { 
                                this.state.accounts.map(
                                account => <option value={account}>{account}</option>) 
                            }
                        </select>
                        <label>Amount </label>
                        <input type="number" value={this.state.amount} name="amount" step=".01" required onChange={this.handleChange}/>
                    </div>
                    <div className="column_two">
                        <label>Note: </label>
                        <textarea name="note" value={this.state.note} placeholder="Reason to send money?" required onChange={this.handleChange}/>
                    </div>
                    <div className="column_three">
                        <button className="transaction_submit_button" type="submit" onClick={this.handleSubmit}>Transfer</button>
                        <OutsideTransactionModal 
                        token={this.props.token}
                        updateAccountAndOutstanding={() => this.handleNewAccount()} />
                    </div>
                </div>
            </div>
        );
    }
}

export default CreateTransaction;