package com.example.calc.web.rest

import com.example.calc.service.MathCalculationService
import com.example.calc.service.dto.CalculationResultDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("rest/v1/math-calculation")
class MathCalculationController {

    @Autowired
    private lateinit var mathCalculationService: MathCalculationService

    @GetMapping("/calculate")
    fun calculate(@RequestParam ex: String): ResponseEntity<CalculationResultDto> {
        val result = mathCalculationService.calculate(ex)
        return ResponseEntity.ok(result)
    }
}