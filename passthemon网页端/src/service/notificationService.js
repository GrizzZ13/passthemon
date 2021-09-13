import {url} from "../config/url";
import {postRequest_v2,POST_V2} from "../utils/Axios";
export const publish=(data,callback) =>{
    const request = `${url}/notification/publish`;
    POST_V2(request,data,callback);
}
