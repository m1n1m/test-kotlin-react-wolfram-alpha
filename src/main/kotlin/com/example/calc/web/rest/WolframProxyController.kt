package com.example.calc.web.rest

import com.example.calc.service.WolframAlphaService
import com.example.calc.service.dto.CalculationResultDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("rest/v1/wolfram-alpha-proxy")
class WolframProxyController {

    @Autowired
    private lateinit var wolframAlphaService: WolframAlphaService

    @GetMapping("/calculate")
    fun calculate(@RequestParam ex: String): ResponseEntity<CalculationResultDto> {
        val result = wolframAlphaService.calculate(ex)
        return ResponseEntity.ok(result)
    }
}