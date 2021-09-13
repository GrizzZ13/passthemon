import {GET} from '../utils/Axios';
import {url} from '../config/url';

export const listFavoriteByPage = (data, callback) => {
    const request = `${url}/Favorite/listFavoriteByPage`;
    GET(request, data, callback);
};

export const addFavorite = (data, callback) => {
    const request = `${url}/Favorite/addFavorite`;
    GET(request, data, callback);
};

export const deleteFavorite = (data, callback) => {
    const request = `${url}/Favorite/deleteFavorite`;
    GET(request, data, callback);
};

export const checkFavorite = (data, callback) => {
    const request = `${url}/Favorite/checkFavorite`;
    GET(request, data, callback);
};

export const deleteAllFavorite = (data, callback) => {
    const request = `${url}/Favorite/deleteAllFavorite`;
    GET(request, data, callback);
};
