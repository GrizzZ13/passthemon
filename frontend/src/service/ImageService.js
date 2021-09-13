import * as Axios from '../utils/Axios';
import {url} from '../config/url';
import {postRequest_v2} from "../utils/Ajax";
export const getAllImgByGoodsId = (data, callback) => {
  const request = `${url}/images/getAllImgByGoodsId`;
  Axios.GET(request, data, callback);
};

export const getAllImgForThisPage = (data, callback) => {
  const request = `${url}/images/getAllImgForThisPage`;
  Axios.POST_V2(request, data, callback);
};
