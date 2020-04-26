package com.example.calc.service.impl

import com.example.calc.service.MathCalculationService
import com.example.calc.service.dto.CalculationResultDto
import org.springframework.stereotype.Service

@Service
class MathCalculationServiceImpl : MathCalculationService {

    private val parser = MathParser()

    override fun calculate(exp: String, min: Double, max: Double, points: Int): CalculationResultDto {
        val calculationResult = CalculationResultDto()
        try {
            parser.parseExpression(exp)

            val result = Array(points) { DoubleArray(2) }
            val delta = (max - min) / (points - 1)

            for (i in 0 until points) {
                result[i][0] = min + i * delta
                result[i][1] = parser.evaluate(result[i][0])
            }

            calculationResult.result = result

        } catch (e: Exception) {
            calculationResult.error = e.message
        }
        return calculationResult
    }
}