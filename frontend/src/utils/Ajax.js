import storage from '../config/Storage';

let postRequest = (url, json, callback) => {
  storage.save({key: 'loginState'}).then(ret => {
    let opts = {
      method: 'POST',
      body: JSON.stringify(json),
      headers: {
        'Content-Type': 'application/json',
        Token: ret.token,
      },
      credentials: 'include',
    };

    fetch(url, opts)
      .then(response => {
        return response.json();
      })
      .then(data => {
        callback(data);
      })
      .catch(error => {
        console.log(error);
      });
  });
};

let postRequest_v2 = (url, data, callback) => {
  let formData = new FormData();
  for (let p in data) {
    if (data.hasOwnProperty(p)) {
      formData.append(p, data[p]);
    }
  }

  let opts = {
    method: 'POST',
    body: formData,
    credentials: 'include',
  };

  fetch(url, opts)
    .then(response => {
      return response.json();
    })
    .then(data => {
      callback(data);
    })
    .catch(error => {
      console.log(error);
    });
};

export {postRequest, postRequest_v2};
