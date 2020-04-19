package com.example.calc.service.impl

import com.example.calc.service.WolframAlphaService
import com.example.calc.service.dto.CalculationResultDto
import com.example.calc.service.rest.wolfram.WolframAlphaFullResultsApiService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WolframAlphaServiceImpl : WolframAlphaService {

    @Autowired
    private lateinit var wolframAlphaFullResults: WolframAlphaFullResultsApiService

    override fun calculate(expression: String): CalculationResultDto {
        val calculationResult = CalculationResultDto()
        try {
            calculationResult.value = wolframAlphaFullResults.query(expression).toDouble()
        } catch (e: Exception) {
            calculationResult.error = "Wolfram error"
        }
        return calculationResult
    }
}