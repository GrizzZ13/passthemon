import {url} from '../config/url';
import {GET, POST, postRequest, postRequest_v2} from '../utils/Axios';
export const makeOrder = (params, callback) => {
  const request = `${url}/order/makeOrder`;
  GET(request, params, callback);
};
export const getOrdersByUserId = (params, callback, opt = -1) => {
  let request;
  switch (opt) {
    case 0: request = `${url}/order/getOrdersAsBuyerByUserid`; break;
    case 1: request = `${url}/order/getOrdersAsSellerByUserid`; break;
    default: request = `${url}/order/getOrdersByUserId`; break;
  }
  GET(request, params, callback);
};
export const commentOnOrderByUserId = (param, callback) => {
  const request = `${url}/order/commentAndRateOnOrder`;
  GET(request, param, callback);
};
export const changeOrderStatus = (param, callback) => {
  const request = `${url}/order/changeOrderStatus`;
  GET(request, param, callback);
}
export const affirmWants = (param, callback) => {
  const request = `${url}/order/affirmWants`;
  GET(request, param, callback);
}
