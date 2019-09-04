/* eslint no-console: 0 */

export const FETCH_METHODS = {
  GET: 'GET',
  POST: 'POST',
  PUT: 'PUT',
  DELETE: 'DELETE',
  PATCH: 'PATCH'
}

export const apiFetch =
  ({ baseUrl, endPoint, method, token = null, body = null, params = null, headers = null }) =>
    new Promise((resolve, reject) => {

      let defaultHeaders = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      };

      token && (defaultHeaders['Authorization'] = `Bearer ${token}`);

      const opts = {
        method,
        headers: {
          ...defaultHeaders,
          ...headers
        },
        body: body ? JSON.stringify(body) : null
      };

      const urlParms = params ?
        '?' + Object.keys(params).map(key => key + '=' + params[key]).join('&')
        :
        '';

      const uri = `${baseUrl}${!!endPoint ? endPoint : ''}${urlParms}`;

      console.log("react-native-widgets::api::request", {
        opts,
        uri,
      });

      fetch(uri, opts)
        .then(response => {
          if (response.ok) {
            response.json().then(jsonResponse => {
              console.log('react-native-widgets::api::jsonResponse', jsonResponse);
              resolve(jsonResponse);
            }).catch(reject);
          } else {
            response.json()
              .then(errJson => {
                console.log('react-native-widgets::api::errJson', errJson);
                reject({ status: response.status, error: ((errJson.error) ? errJson.error : errJson) })
              }).catch(reject);
          }
        })
        .catch((error) => {
          console.log('react-native-widgets::api::error', error);
          reject(error);
        });
    });