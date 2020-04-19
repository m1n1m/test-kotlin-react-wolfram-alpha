package com.example.calc.service.rest.wolfram

interface WolframAlphaFullResultsApiService {

    fun query(input: String): String
}