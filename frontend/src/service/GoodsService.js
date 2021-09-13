import {GET, POST_V2} from '../utils/Axios';
import {url} from '../config/url';
export const editGood = (data, callback) => {
  const request = `${url}/goods/editGood`;
  POST_V2(request, data, callback);
};

export const addGood = (data, callback) => {
  const request = `${url}/goods/addGood`;
  POST_V2(request, data, callback);
};
export const removeGoods = (data,callback) => {
  const request = `${url}/goods/removeGoods`;
  GET(request, data, callback);
};
export const listGoodsByPage = (data, callback) => {
  const request = `${url}/goods/listGoodsByPage`;
  GET(request, data, callback);
};

export const listMyGoodsByPage = (data, callback) => {
  const request = `${url}/goods/listMyGoodsByPage`;
  GET(request, data, callback);
};

export const findGoodsById = (data, callback) => {
  const request = `${url}/goods/findGoodsById`;
  GET(request, data, callback);
};

//下面这个和上面的区别在于参数，下边只需要goodId，上边那个需要goodsId和userid
export const getGoodsById = (data, callback) => {
  const request = `${url}/goods/getGoodsById`;
  GET(request, data, callback);
};

export const searchGoods = (data, callback) => {
  const request = `${url}/goods/searchGoods`;
  GET(request, data, callback);
};

export const getWanter = (data, callback) => {
  const request = `${url}/goods/getWanter`;
  GET(request, data, callback);
};
export const getMaxGoodsPage = (data, callback) => {
  const request = `${url}/goods/getMaxGoodsPage`;
  GET(request, data, callback);
};
