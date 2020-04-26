package com.example.calc.service.rest.wolfram

import com.example.calc.service.dto.CallWolframAlphaPlotImageResultDto

interface WolframAlphaFullResultsApiService {

    fun query(input: String): CallWolframAlphaPlotImageResultDto
}