import React from 'react';
import {history} from "./utils/history";
import {emitter} from "./utils/emitter";
import { Router, Route, Switch, Redirect} from 'react-router-dom';
import {Home} from "./component/home";
import {Logout} from "./component/logout";
import {LoginScreen} from "./component/login";
import {Error} from "./component/Error";

export class RouteControl extends React.Component {
    constructor(props) {
        super(props);
    }

    state = {
        isLogin: false,
        userid: '',
        token: '',
    }

    componentDidMount() {
        this.eventEmitter=emitter.addListener("changeIsLogin",(val)=>{
            if(val.type==1){
                this.setState({
                    isLogin:true,
                });
            }else if(val.type==2){
                this.setState({isLogin:false})
            }
        });
        let flag=localStorage.getItem('loginState')
            ? true : false;
        if(flag){
            let val=JSON.parse(localStorage.getItem('loginState'));
            this.setState({
                isLogin:true,
                userid:val.userid,
                token:val.token,
            })
        }
    }
    componentWillUnmount() {
        this.eventEmitter.remove();
    }

    render(){
        if(this.state.isLogin){
            return (
                <Router history={history}>
                    <Switch>
                        <Route exact path="/" component={Home}/>
                        <Route exact path="/Logout" component={Logout}/>
                        <Route exact path="/error" component={Error}/>
                        <Redirect from="/*" to="/" />
                    </Switch>
                </Router>
            )
        }
        else return (
            <Router history={history}>
                <Switch>
                    <Switch>
                        <Route exact path="/" component={LoginScreen}/>
                        <Redirect from="/*" to="/" />
                    </Switch>
                </Switch>
            </Router>
        )
    }
}
