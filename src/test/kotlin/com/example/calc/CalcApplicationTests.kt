package com.example.calc

import com.example.calc.service.MathCalculationService
import com.example.calc.service.WolframAlphaService
import com.example.calc.service.impl.MathParser
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CalcApplicationTests {

    @Autowired
    private lateinit var wolframAlphaService: WolframAlphaService
    @Autowired
    private lateinit var mathCalculationService: MathCalculationService

    @Test
    fun testMathParserWithoutVar() {
        val exp = "2 * 3 - 12 * (99-2.332)"// "x = 3; 2 * x - 12"
        val parser = MathParser()
        val result = parser.evaluate(exp)
        Assert.assertEquals(result, -1154.016, 0.0)
    }

    @Test
    fun testMathParserAndWolframServices() {
        val exp = "2 * 3 - 12 * (99-2.332)"
        val wolfResult = wolframAlphaService.calculate(exp).value!!
        val result = mathCalculationService.calculate(exp).value!!
        Assert.assertEquals(wolfResult, result, 0.0)
    }

    @Test
    fun testMathParserVar() {
        val exp = "x = 3; y = -2.332; 2 * x - 12 * (99-y)"
        val parser = MathParser()
        val result = parser.evaluate(exp)
        Assert.assertEquals(result, -1209.984, 0.0)
    }

    @Test
    fun testMathParserVariableNotSet() {
        val exp = "a = 3; y = -2.332; 2 * x - 12 * (99-y)"
        val parser = MathParser()
        Assert.assertThrows("Variable [x] is not set", RuntimeException::class.java) {
            parser.evaluate(exp)
        }
    }
}
