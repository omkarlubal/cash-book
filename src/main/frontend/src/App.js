import './App.css';
import React from 'react';
import Login from './components/login/Login';
import {Switch, Route} from 'react-router-dom';
import Home from './components/home/Home';
import AuthService from './services/AuthService';
import 'react-app-polyfill/stable';

import 'bootstrap/dist/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.min.css';

const token = localStorage.getItem("hmToken");

class App extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
        isLog:false,
      }
  }

  handleLogin = (isLog) => this.setState({isLog : isLog});

  validateToken(token) {
    if (token) {
      AuthService.checkTokenValid(token).then((response) => {
        let success = response.data.success;
        console.log(response);
        if (success) {
            this.setState({isLog : true});
        }
      });
    }
  }
  
  componentDidMount() {
    this.validateToken(token);
  }

  render () {
    const isLog = this.state.isLog;
    
    return (
      <div className="App">
        <Switch>
          <Route exact path='/' render={()=> !isLog ? <Login isLogin={this.handleLogin}/> : <Home/> }/>
        </Switch>
      </div>
    );
  }
}

export default App;
