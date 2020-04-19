package com.example.calc.service.dto

import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
class CalculationResultDto {
    var value: Double? = null
    var error: String? = null
}