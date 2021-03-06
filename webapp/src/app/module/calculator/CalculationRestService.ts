import CalculationResultDto from "./CalculationResultDto";
import {RestService} from "../../services";

export default class CalculationRestService implements RestService {

    constructor(public baseUrl: string) {

    }

    public calculateExpression(exp: string, min: number, max: number, points: number) : Promise<CalculationResultDto>  {
        if (!exp) {
            return new Promise<CalculationResultDto>(resolve => {});
        }

        let params = [
            ['ex', exp.replace(' ', '')],
            ['min', min.toString()],
            ['max', max.toString()],
            ['points', points.toString()]];

        const urlParams = new URLSearchParams(params).toString();
        const url = `${this.baseUrl}math-calculation/calculate?${urlParams}`;
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        };
        return fetch(url, requestOptions).then(response => response.json());
    }
}