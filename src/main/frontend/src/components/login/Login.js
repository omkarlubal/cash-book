import React from 'react';
import AuthService from '../../services/AuthService';

import './Login.css';

class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username :'',
            pwd : '',
            jwtToken : '',
            history:'',
        }
    }

    handleChange = (e) => {
        const {name,value} = e.target;
        this.setState({
            [name]:value,
        })
    }

    handleSubmit = (e) => {
        let credentials = {
            username:this.state.username,
            password:this.state.pwd
        }

        AuthService.authenticate(credentials).then((response) => {
            let loginResult = response.data.success;
            if (loginResult) {
                // login success
                localStorage.setItem('hmToken',response.data.token);
                this.props.isLogin(true);
            } else {
                alert('Wrong credentials');
            }
        });
        e.preventDefault();
        return false;
    }

    render() {
        return (
        <div>
            <div className="header-login">
                <h1><i className="fas fa-dice-d20"></i>HMDRF</h1>
            </div>
            <div className="login-div">
                <form onSubmit={this.handleSubmit}>
                    <h3>Sign In</h3>
                    <div className="form-group">
                        <label className="auth-label">Username</label>
                        <input type='text' className="form-control" name='username' required onChange={this.handleChange}/>
                    </div>
                    <div className="form-group">
                        <label className="auth-label">Password</label>
                        <input type='password' className="form-control" name='pwd' placeholder="******" required onChange={this.handleChange}/>
                    </div>
                    <button className="btn btn-primary btn-block loginButton" onSubmit={this.handleSubmit}>Log In!</button>
                </form>
                <h1>{this.state.jwtToken}</h1>
            </div>
        </div>
        ); 
    }
}

export default Login;