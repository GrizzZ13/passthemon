import {url} from "../config/url";
import {GET} from "../utils/Axios";


export const addWants = (data, callback) => {
    const request = `${url}/wants/addWants`;
    GET(request, data, callback);
};

export const deleteWants = (data, callback) => {
    const request = `${url}/wants/deleteWants`;
    GET(request, data, callback);
};

export const checkWants = (data, callback) => {
    const request = `${url}/wants/checkWants`;
    GET(request, data, callback);
};
