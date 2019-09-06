import { apiFetch, FETCH_METHODS } from "./api";
import config from "../../config.json";

export const searchStock = (str) => {
    return new Promise((resolve, reject) => {
        apiFetch({
            baseUrl: config.api.baseUrl,
            endPoint: config.api.endpoints.query,
            method: FETCH_METHODS.GET,
            params: {
                function: config.api.params.function.SYMBOL_SEARCH,
                keywords: str,
                apikey: config.api.key,
            }
        })
            .then(res => {
                if(res['Note']) reject(res['Note'])
                resolve(transformArrayResponse(res['bestMatches']));

            }).catch(e => {
                reject(e)
            })
    })
}

export const getStockQuote = (symbol) => {
    return new Promise((resolve, reject) => {
        apiFetch({
            baseUrl: config.api.baseUrl,
            endPoint: config.api.endpoints.query,
            method: FETCH_METHODS.GET,
            params: {
                function: config.api.params.function.GLOBAL_QUOTE,
                symbol,
                apikey: config.api.key,
            }
        })
            .then(res => {
                if(res['Note']) reject(res['Note'])
                resolve(transformResponse(res["Global Quote"]));
            }).catch(e => {
                reject(e)
            })
    })
}

const transformArrayResponse = (arrayResponse) => {
    let output = [];
    arrayResponse.forEach(response => {
        output.push(transformResponse(response))
    })
    return output;
}

const transformResponse = (response) => {
    let output = {};
    Object.keys(response).forEach(key => {
        output[key.split(' ')[1]] = response[key]
    })
    output.updated = (new Date()).toLocaleTimeString();
    return output;
}