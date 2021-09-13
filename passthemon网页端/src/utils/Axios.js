import axios from 'axios';
import {history} from "./history";
import {
    EXPIRED_TOKEN,
    OK,
    PERMISSION_DENIED,
    REQUEST_EXCESSIVE,
    UPDATE_TOKEN,
    VISITING_TOO_OFTEN
} from '../config/Message';
axios.defaults.withCredentials = false
async function getLocalToken(){
    let val = await JSON.parse(localStorage.getItem('loginState'));
    return val.token;
}
const instance = axios.create({
    baseURL: '',
    timeout: 300000,
    headers: {
        Token: null, // header's token
    },
});
const instance_v2 = axios.create({
    baseURL: '',
    timeout: 300000,
    headers: {
        'Content-Type': 'application/json',
        Token: null, // header's token
    },
});
instance_v2.setToken = token => {
    instance_v2.defaults.headers.Token = token;
    instance_v2.defaults.headers.contentType = 'application/json';
    // storage.load({key: 'loginState'}).then(ret => {
    //     storage
    //         .save({
    //             key: 'loginState',
    //             data: {
    //                 userid: ret.userid,
    //                 token: token,
    //             },
    //         })
    //         .then(() => {
    //             console.log('save token-2');
    //         });
    // });
    let info=JSON.parse(localStorage.getItem('loginState'));
    let newInfo={
        userid:info.userid,
        token:token,
    };
    localStorage.setItem('loginState',JSON.stringify(newInfo));
};
instance.setToken = token => {
    instance.defaults.headers.Token = token;
    let info=JSON.parse(localStorage.getItem('loginState'));
    let newInfo={
        userid:info.userid,
        token:token,
    };
    localStorage.setItem('loginState',JSON.stringify(newInfo));
};
instance.interceptors.response.use(
    response => {
        console.log('onFulfilled');
        const msg = response.data;
        console.log("response status "+response.status);
        console.log(msg);
        if (msg.status === UPDATE_TOKEN) {
            console.log("update access token");
            // 说明token过期了,刷新token
            instance.setToken(msg.token);
            const config = response.config;
            // 重置一下配置
            config.headers.Token = msg.token;
            config.baseURL = ''; // url已经带上了/api，避免出现/api/api的情况
            // 重试当前请求并返回promise
            return instance(config);
        } else if (msg.status === EXPIRED_TOKEN) {
            console.warn('jump to logout');
            history.push('/Logout');
            return Promise.reject(response);
        } else if (msg.status === PERMISSION_DENIED){
            history.push('error');
            return Promise.reject(response);
        } else if (msg.status === VISITING_TOO_OFTEN){
            history.push('error');
            return Promise.reject(response);
        }else if(msg.status === REQUEST_EXCESSIVE){
            history.push('error');
            return Promise.reject(response);
        }
        return Promise.resolve(response);
    },
    error => {
        return Promise.reject(error);
    },
);
instance_v2.interceptors.response.use(
    response => {
        console.log('onFulfilled');
        const msg = response.data;
        console.log("response status "+response.status);
        if (msg.status === UPDATE_TOKEN) {
            console.log("shuaxinyixiatoken");
            // 说明token过期了,刷新token
            instance.setToken(msg.token);
            const config = response.config;
            // 重置一下配置
            config.headers.Token = msg.token;
            config.baseURL = ''; // url已经带上了/api，避免出现/api/api的情况
            // 重试当前请求并返回promise
            return instance(config);
        } else if (msg.status === EXPIRED_TOKEN) {
            console.warn('jump to logout');
            history.push('/logout');
        } else if (msg.status === PERMISSION_DENIED){
            history.push('error');
            return Promise.reject(response);
        } else if (msg.status === VISITING_TOO_OFTEN){
            history.push('error');
            return Promise.reject(response);
        } else if(msg.status === REQUEST_EXCESSIVE){
            history.push('error');
            return Promise.reject(response);
        }
        return Promise.resolve(response);
    },
    error => {
        return Promise.reject(error);
    },
);
export function GET(url, params, callback) {
    console.log(url);
    getLocalToken()
        .then(token => {
            console.log('token'+token);
            instance.setToken(token);
        })
        .then(() => {
            instance
                .get(url, {params: params})
                .then(res => res.data)
                .then(data => {
                    if (typeof callback === 'function') {
                        callback(data.data);
                    } else if (callback == null) {
                        console.log('Warning: callback is null!');
                    } else {
                        console.log('Error: callback is not a function!');
                    }
                })
                .catch(err => {
                    console.log('catch exception');
                    console.log(err);
                });
        });
}
export function POST(url, params, callback) {
    getLocalToken()
        .then(token => {
            instance.setToken(token);
        })
        .then(() => {
            let formData = new FormData();
            for (let param in params) {
                if (params.hasOwnProperty(param)) {
                    formData.append(param, params[param]);
                }
            }
            instance
                .post(url, formData)
                .then(res => res.data)
                .then(data => {
                    if (typeof callback === 'function') {
                        callback(data.data);
                    } else if (callback == null) {
                        console.log('Warning: callback is null!');
                    } else {
                        console.log('Error: callback is not a function!');
                    }
                });
        });
}
export function POST_V2(url, params, callback) {
    getLocalToken()
        .then(token => {
            instance_v2.setToken(token);
        })
        .then(() => {
            instance_v2
                .post(url, JSON.stringify(params))
                .then(res => res.data)
                .then(data => {
                    console.log(data);
                    if (typeof callback === 'function') {
                        callback(data.data);
                    } else if (callback == null) {
                        console.log('Warning: callback is null!');
                    } else {
                        console.log('Error: callback is not a function!');
                    }
                })
                .catch(err => {
                    console.log(err);
                    console.log('catch exception');
                });
        });
}
export function GET_OLD(url, params, callback) {
    // storage.load({key: 'loginState'}).then(ret => {
    //     GET_INNER(url, params, callback, ret.token);
    // });
    let ret=JSON.parse(localStorage.getItem('loginState'));
    GET_INNER(url,params,callback,ret.token);
}

export function GET_INNER(url, params, callback, token) {
    axios({
        url: url,
        method: 'get',
        params: params,
        headers: {
            Token: token,
        },
    })
        .then(res => {
            return res.data;
        })
        .then(data => {
            if (typeof callback === 'function') {
                alert(data.status);
                switch (data.status) {
                    case OK:
                        callback(data.data);
                        break;
                    case EXPIRED_TOKEN:
                        // NavigationService.navigate('Logout');
                        history.push('Logout');
                        break;
                    case UPDATE_TOKEN:
                        alert(data.token);
                        let val=JSON.parse(localStorage.getItem('loginState'));
                        let newVal={
                            userid:val.userid,
                            token:data.token,
                        }
                        localStorage.setItem('loginState',JSON.stringify(newVal));
                        // storage.load({key: 'loginState'}).then(ret => {
                        //     storage.save({
                        //         key: 'loginState',
                        //         data: {
                        //             userid: ret.userid,
                        //             token: data.token,
                        //         },
                        //     });
                        // });
                        callback(data.data);
                        break;
                }
            } else if (callback == null) {
                console.log('Warning: callback is null!');
            } else {
                console.log('Error: callback is not a function!');
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/* 'axios.post' function package
    ex:
        import * as Axios from '...'

        const url = 'http://192.168.43.185:9090/order/Order';
        let params = {userId : 1};
        let callback = (data) => {
            console.log(data);
            alert(JSON.stringify(data));
        };
        Axios.POST(url, params, callback);
    info:
        url must be correct,
        params can be 'null' or '{}',
        callback can be 'null'
*/
export function POST_OLD(url, params, callback) {
    // storage.load({key: 'loginState'}).then(ret => {
    //     POST_INNER(url, params, callback, ret.token);
    // });
    let ret=JSON.parse(localStorage.getItem('loginState'));
    POST_INNER(url,params,callback,ret.token);
}
export function POST_INNER(url, params, callback, token) {
    let formData = new FormData();
    for (let param in params) {
        if (params.hasOwnProperty(param)) {
            formData.append(param, params[param]);
        }
    }
    axios({
        url: url,
        method: 'post',
        data: formData,
        headers: {
            Token: token,
        },
    })
        .then(response => {
            return response.data;
        })
        .then(function (data) {
            if (typeof callback === 'function') {
                alert(data.status);
                switch (data.status) {
                    case OK:
                        callback(data.data);
                        break;
                    case EXPIRED_TOKEN:
                        // NavigationService.navigate('Logout');
                        history.push('Logout');
                        break;
                    case UPDATE_TOKEN:
                        alert(data.token);
                        // storage.load({key: 'loginState'}).then(ret => {
                        //     storage.save({
                        //         key: 'loginState',
                        //         data: {
                        //             userid: ret.userid,
                        //             token: data.token,
                        //         },
                        //     });
                        // });
                        let ret=JSON.parse(localStorage.getItem('loginState'));
                        let newVal={
                            userid:ret.userid,
                            token:data.token,
                        }
                        localStorage.setItem('loginState',JSON.stringify(newVal));
                        callback(data.data);
                        break;
                }
            } else if (callback == null) {
                console.log('Warning: callback is null!');
            } else {
                console.log('Error: callback is not a function!');
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/* 'axios.get' function package using a different style
    ex:
        import * as Axios from '...'

        const url = 'http://192.168.43.185:9090/order/Order';
        let params = {userId : 1};
        Axios.GET(url, params).then(data => {
            console.log(data);
            alert(JSON.stringify(data));
        };
    info:
        url must be correct,
        params can be 'null' or '{}'
*/
export function GET_ASYNC(url, params) {
    return new Promise((resolve, reject) => {
        axios
            .get(url, {
                params: params,
            })
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
}

let postRequest = (url, json, callback) => {
    // storage.load({key: 'loginState'}).then(ret => {
    //     postRequestInner(url, json, callback, ret.token);
    // });
    let ret=JSON.parse(localStorage.getItem('loginState'));
    postRequestInner(url, json, callback, ret.token);
};
let postRequestInner = (url, json, callback, token) => {
    let opts = {
        method: 'POST',
        body: JSON.stringify(json), //把json对象转变成json字符串
        headers: {
            'Content-Type': 'application/json',
            Token: token,
        },
        credentials: 'include',
    };
    // console.log("url="+url);
    // console.log("opts="+opts);
    fetch(url, opts)
        .then(response => {
            return response.json();
        })
        .then(data => {
            if (data.status == EXPIRED_TOKEN) {
                // NavigationService.navigate('Logout');
                history.push('Logout');
            } else if (data.status == UPDATE_TOKEN) {
                // storage.load({key: 'loginState'}).then(ret => {
                //     storage.save({
                //         key: 'loginState',
                //         data: {
                //             userid: ret.userid,
                //             token: data.token,
                //         },
                //     });
                // });
                let ret=JSON.parse(localStorage.getItem('loginState'));
                let newVal={
                    userid:ret.userid,
                    token:data.token,
                }
                callback(data.data);
            } else {
                callback(data.data);
            }
        })
        .catch(error => {
            console.log(error);
        });
};
let postRequest_v2 = (url, data, callback) => {
    let formData = new FormData();

    for (let p in data) {
        if (data.hasOwnProperty(p)) {
            formData.append(p, data[p]);
        }
    }

    let opts = {
        method: 'POST',
        body: formData,
        credentials: 'include',
    };

    fetch(url, opts)
        .then(response => {
            return response.json();
        })
        .then(data => {
            callback(data);
        })
        .catch(error => {
            console.log(error);
        });
};
export {postRequest, postRequest_v2};
