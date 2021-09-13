import {GET, POST} from "../utils/Axios";
import {url} from "../config/url";

export const getChannelLists = (data, callback) => {
    const request = `${url}/channel/getChannels`
    GET(request, data, callback);
}

export const getChatHistory = (data, callback) => {
    const request = `${url}/channel/getHistory`;
    GET(request, data, callback);
}

export const initChatHistory = (data, callback) => {
    const request = `${url}/channel/initHistory`;
    GET(request, data, callback);
}
