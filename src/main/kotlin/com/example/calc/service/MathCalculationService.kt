package com.example.calc.service

import com.example.calc.service.dto.CalculationResultDto

interface MathCalculationService {
    fun calculate(exp: String, min: Double, max: Double, points: Int): CalculationResultDto
}