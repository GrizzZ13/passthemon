import {url} from '../config/url';
import {GET, POST_V2} from '../utils/Axios';

export const addDemand = (data, callback) => {
  const request = `${url}/demand/addDemand`;
  POST_V2(request, data, callback);
};

export const listAllDemandsByPage = (data, callback) => {
  const request = `${url}/demand/listAllDemandsByPage`;
  GET(request, data, callback);
};

export const listOnesDemandsByPage = (data, callback) => {
  const request = `${url}/demand/listOnesDemandsByPage`;
  GET(request, data, callback);
};

export const getDemandById = (data, callback) => {
  const request = `${url}/demand/getDemandById`;
  GET(request, data, callback);
};
export const editDemand = (data, callback) => {
  const request = `${url}/demand/editDemand`;
  POST_V2(request, data, callback);
};
export const removeDemand = (data, callback) => {
  const request = `${url}/demand/removeDemand`;
  GET(request,data,callback);
}
