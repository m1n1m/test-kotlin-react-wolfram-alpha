import CalculationRestService from "./module/calculator/CalculationRestService";
import {APP_URL} from "../config";
import WolframProxyRestService from "./module/calculator/WolframProxyRestService";

export interface RestService {
    baseUrl: string;
}

export var restServices = {
    calculationRestService: new CalculationRestService(APP_URL),
    wolframProxyRestService: new WolframProxyRestService(APP_URL)
};