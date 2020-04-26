package com.example.calc.service.dto

import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
class CallWolframAlphaPlotImageResultDto {
    var success: Boolean? = null
    var plotImageUrl: String? = null
}