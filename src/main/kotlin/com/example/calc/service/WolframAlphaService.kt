package com.example.calc.service

import com.example.calc.service.dto.CalculationResultDto

interface WolframAlphaService {
    fun calculate(expression: String): CalculationResultDto
}