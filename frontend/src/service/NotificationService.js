import {url} from '../config/url';
import {GET, POST_V2} from '../utils/Axios';
export const getAllOrdinary = (param, callback) => {
    const request = `${url}/notification/getAllOrdinary`;
    GET(request, param, callback);
};

export const checkStatusCode = (param, callback) =>{
    const request = `${url}/notification/checkStatusCode`;
    POST_V2(request,param,callback);
};

export const getDialog = (param, callback) => {
    const request = `${url}/notification/getDialog`;
    GET(request, param, callback);
}
