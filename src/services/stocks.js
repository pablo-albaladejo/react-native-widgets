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
                resolve(res.bestMatches);

            }).catch(e => {
                reject(e)
            })
    })
} 