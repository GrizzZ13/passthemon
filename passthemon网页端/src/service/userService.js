import {url} from "../config/url";
import {postRequest_v2} from "../utils/Axios";
import * as Axios from '../utils/Axios';
export const Login = (data,callback) =>{
    const request=`${url}/user/login`;
    postRequest_v2(request,data,callback);
}
export const getUserInfoByUserId = (data, callback) => {
    const request = `${url}/user/getUserInfoByUserId`;
    Axios.GET(request, data, callback);
};
