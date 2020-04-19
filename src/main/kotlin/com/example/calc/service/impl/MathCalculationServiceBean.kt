package com.example.calc.service.impl

import com.example.calc.service.MathCalculationService
import com.example.calc.service.dto.CalculationResultDto
import org.springframework.stereotype.Service

@Service
class MathCalculationServiceBean : MathCalculationService {

    private val parser = MathParser()

    override fun calculate(expression: String): CalculationResultDto {
        val calculationResult = CalculationResultDto()
        try {
            calculationResult.value = parser.evaluate(expression)
        } catch (e: Exception) {
            calculationResult.error = e.message
        }
        return calculationResult
    }
}