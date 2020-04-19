package com.example.calc.service

import com.example.calc.service.dto.CalculationResultDto

interface MathCalculationService {
    fun calculate(expression: String): CalculationResultDto
}