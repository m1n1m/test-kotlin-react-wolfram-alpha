package com.example.calc;

import com.example.calc.service.impl.MathParser
import org.junit.Assert
import org.junit.jupiter.api.Test

class MathParserUnitTests {

    @Test
    fun testMathParserWithoutVar() {
        val parser = MathParser()
        parser.parseExpression("2 * 3 - 12 * (99-2.332)")
        val result = parser.evaluate()
        Assert.assertEquals(result, -1154.016, 0.0)
    }

    @Test
    fun testMathParserVar() {
        val parser = MathParser()
        parser.parseExpression("2 * x - 12 * (99--2.332)")
        val result = parser.evaluate(3.0)
        Assert.assertEquals(result, -1209.984, 0.0)
    }

    @Test
    fun testMathParserVariableNotSetException() {
        val parser = MathParser()
        parser.parseExpression("2 * x - 12 * (99--2.332)")
        Assert.assertThrows("Variable [x] is not set", RuntimeException::class.java) {
            parser.evaluate()
        }
    }

    @Test
    fun testMathParserPow() {
        val parser = MathParser()
        parser.parseExpression("135-556 * 7 / 2  ^ 2 ^ 2 - (12*4.333)")
        val result = parser.evaluate()
        Assert.assertEquals(result, -160.246, 0.0)
    }
}
