package com.example.calc.web.rest

import com.example.calc.service.WolframAlphaService
import com.example.calc.service.dto.CallWolframAlphaPlotImageResultDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("rest/v1/wolfram-alpha-proxy")
class WolframProxyController(private val wolframAlphaService: WolframAlphaService) {

    @GetMapping("/calculate")
    fun calculate(@RequestParam ex: String): ResponseEntity<CallWolframAlphaPlotImageResultDto> {
        val result = wolframAlphaService.calculate(ex)
        return ResponseEntity.ok(result)
    }
}