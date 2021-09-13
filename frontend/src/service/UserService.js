import {url} from '../config/url';
import {postRequest, postRequest_v2} from '../utils/Ajax';
import * as Axios from '../utils/Axios';
export const CheckToken = (data, callback) => {
  console.log('TODO');
};
export const Login = (data, callback) => {
  const request = `${url}/user/login`;

  postRequest_v2(request, data, callback);
};

export const GetCode = (data, callback) => {
  const request = `${url}/user/sendEmail`;
  postRequest_v2(request, data, callback);
};

export const SignupFunc = (data, callback) => {
  const request = `${url}/user/signup`;
  postRequest_v2(request, data, callback);
};

export const ResetFunc = (data, callback) => {
  const request = `${url}/user/reset`;
  postRequest_v2(request, data, callback);
};

export const getUserByUserId = (data, callback) => {
  const request = `${url}/user/getUserByUserId`;
  Axios.GET(request, data, callback);
};
export const getUserInfoByUserId = (data, callback) => {
  const request = `${url}/user/getUserInfoByUserId`;
  Axios.GET(request, data, callback);
};

export const submitChange = (data, callback) => {
  const request = `${url}/user/submitChange`;
  Axios.POST_V2(request, data, callback);
};
export const getOtherInfo = (data, callback) => {
  const request = `${url}/user/getOtherInfo`;
  Axios.GET(request, data, callback);
};
//关注他人
export const followOther = (data, callback) => {
  const request = `${url}/user/followOther`;
  Axios.GET(request, data, callback);
};
//取消关注
export const unFollowOther = (data, callback) => {
  const request = `${url}/user/unFollowOther`;
  Axios.GET(request, data, callback);
};
//获得关注列表
export const getUsersByPage = (data, callback) => {
  const request = `${url}/user/getUsersByPage`;
  Axios.GET(request, data, callback);
};
//根据用户id获得username
export const getUserNameById = (data, callback) => {
  const request = `${url}/user/getUserNameById`;
  Axios.GET(request, data, callback);
}
