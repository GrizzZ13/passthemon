import {GET} from "../utils/Axios";
import {url} from "../config/url";

export const listHistory = (data, callback) => {
    const request = `${url}/history/listHistory`;
    GET(request, data, callback);
};

export const deleteAllHistory = (data, callback) => {
    const request = `${url}/history/deleteAllHistory`;
    GET(request, data, callback);
};

export const listHistoryByTime = (data, callback) => {
    const request = `${url}/history/listHistoryByTime`;
    GET(request, data, callback);
};
