import {RestService} from "../../services";
import CallWolframAlphaPlotImageResultDto from "./CallWolframAlphaPlotImageResultDto";

export default class WolframProxyRestService implements RestService {

    constructor(public baseUrl: string) {

    }

    public calculateExpression(exp: string) : Promise<CallWolframAlphaPlotImageResultDto>  {
        if (!exp) {
            return new Promise<CallWolframAlphaPlotImageResultDto>(resolve => {});
        }
        let params = [['ex', exp.replace(' ', '')]];
        const urlParams = new URLSearchParams(params).toString();
        const url = `${this.baseUrl}wolfram-alpha-proxy/calculate?${urlParams}`;
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        };
        return fetch(url, requestOptions).then(response => response.json());
    }
}