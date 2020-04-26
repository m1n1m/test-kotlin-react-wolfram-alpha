package com.example.calc.service

import com.example.calc.service.dto.CallWolframAlphaPlotImageResultDto

interface WolframAlphaService {
    fun calculate(expression: String): CallWolframAlphaPlotImageResultDto
}