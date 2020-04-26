package com.example.calc.service.impl

import com.example.calc.service.WolframAlphaService
import com.example.calc.service.dto.CallWolframAlphaPlotImageResultDto
import com.example.calc.service.rest.wolfram.WolframAlphaFullResultsApiService
import org.springframework.stereotype.Service

@Service
class WolframAlphaServiceImpl(
        private val wolframAlphaFullResults: WolframAlphaFullResultsApiService) : WolframAlphaService {

    override fun calculate(expression: String): CallWolframAlphaPlotImageResultDto {
        var calculationResult = CallWolframAlphaPlotImageResultDto()
        try {
            calculationResult = wolframAlphaFullResults.query(expression)
        } catch (e: Exception) {
        }
        return calculationResult
    }
}