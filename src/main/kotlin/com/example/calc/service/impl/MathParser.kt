package com.example.calc.service.impl

import java.util.*
import kotlin.math.pow

class MathParser {

    private var lexemes: List<Lexeme>? = null
    private var pos = 0
    private var x: Double? = null
    private var exp: String? = null

    constructor()

    constructor(exp: String) {
        this.exp = exp
    }

    fun parseExpression(exp: String) {
        if (exp.isNullOrBlank()) {
            throw RuntimeException("Empty Expression")
        }
        lexemes = analyzeLexemes(exp.replace("\\s+".toRegex(), ""))
    }

    fun evaluate(): Double {
        pos = 0
        this.x = null
        return evalPlusMinus()
    }

    fun evaluate(x: Double): Double {
        pos = 0
        this.x = x
        return evalPlusMinus()
    }

    private fun riseUnexpectedTokenException(lexeme: Lexeme) {
        throw RuntimeException("Unexpected token: $lexeme.value at position: $pos")
    }

    private fun evalPlusMinus(): Double {
        var value = evalMultiplyDiv()
        while (true) {
            val lexeme = stepNextLexeme()
            when (lexeme.type) {
                LexemeType.OP_PLUS -> value += evalMultiplyDiv()
                LexemeType.OP_MINUS -> value -= evalMultiplyDiv()
                LexemeType.EOF, LexemeType.RIGHT_BRACKET -> {
                    pos--
                    return value
                }
                else -> riseUnexpectedTokenException(lexeme)
            }
        }
    }

    private fun evalMultiplyDiv(): Double {
        var value = evalPow()
        while (true) {
            val lexeme = stepNextLexeme()
            when (lexeme.type) {
                LexemeType.OP_MUL -> value *= evalPow()
                LexemeType.OP_DIV -> value /= evalPow()
                LexemeType.EOF, LexemeType.RIGHT_BRACKET, LexemeType.OP_PLUS, LexemeType.OP_MINUS -> {
                    pos--
                    return value
                }
                else -> riseUnexpectedTokenException(lexeme)
            }
        }
    }

    private fun evalPow(): Double {
        var value = evalUnary()
        while (true) {
            if (currentLexeme().type === LexemeType.POW) {
                stepNextLexeme()
                val rightValue = evalPow()
                value = value.pow(rightValue)
            }
            else {
                return value
            }
        }
    }

    private fun evalUnary(): Double {
        val lexeme = currentLexeme()
        if (lexeme.type === LexemeType.OP_MINUS || lexeme.type === LexemeType.OP_PLUS) {
            val nextLexeme = getNextLexeme()
            if (nextLexeme.type === LexemeType.NUMBER || nextLexeme.type === LexemeType.LEFT_BRACKET) {
                stepNextLexeme()
                return if (lexeme.type === LexemeType.OP_MINUS) -factor() else factor()
            }
        }
        return factor()
    }

    private fun factor(): Double {
        var lexeme = stepNextLexeme()
        return when (lexeme.type) {
            LexemeType.NUMBER -> lexeme.value.toDouble()
            LexemeType.VAR -> {
                if (lexeme.value == "x" && x != null) {
                    x!!
                } else {
                    throw RuntimeException("Variable [${lexeme.value}] is not set")
                }
            }
            LexemeType.LEFT_BRACKET -> {
                val value = evalPlusMinus()
                lexeme = stepNextLexeme()
                if (lexeme.type !== LexemeType.RIGHT_BRACKET) {
                    riseUnexpectedTokenException(lexeme)
                }
                value
            }
            else -> throw RuntimeException("Unexpected token: $lexeme.value at position: $pos")
        }
    }

    private fun stepNextLexeme(): Lexeme {
        return lexemes!![pos++]
    }

    private fun getNextLexeme(): Lexeme {
        return lexemes!![pos + 1]
    }

    private fun currentLexeme(): Lexeme {
        return lexemes!![pos]
    }

    private fun analyzeLexemes(exp: String): List<Lexeme> {

        val lexemes: MutableList<Lexeme> = LinkedList()
        var pos = 0

        while (pos < exp.length) {
            var c = exp[pos]
            when (c) {
                '(' -> { lexemes.add(Lexeme(LexemeType.LEFT_BRACKET, c)); pos++ }
                ')' -> { lexemes.add(Lexeme(LexemeType.RIGHT_BRACKET, c)); pos++ }
                '+' -> { lexemes.add(Lexeme(LexemeType.OP_PLUS, c)); pos++ }
                '-' -> { lexemes.add(Lexeme(LexemeType.OP_MINUS, c)); pos++ }
                '*' -> { lexemes.add(Lexeme(LexemeType.OP_MUL, c)); pos++ }
                '/' -> { lexemes.add(Lexeme(LexemeType.OP_DIV, c)); pos++ }
                '^' -> { lexemes.add(Lexeme(LexemeType.POW, c)); pos++ }
                'x', 'X' -> { lexemes.add(Lexeme(LexemeType.VAR, c.toString().toLowerCase())); pos++ }
                else -> if (c in '0'..'9') {
                    var dots = 0
                    val sb = StringBuilder()
                    do {
                        if (c == '.' && ++dots > 1) {
                            throw RuntimeException("Unexpected character: $c")
                        }
                        sb.append(c)
                        pos++
                        if (pos >= exp.length) {
                            break
                        }
                        c = exp[pos]
                    } while (c in '0'..'9' || c == '.')
                    lexemes.add(Lexeme(LexemeType.NUMBER, sb.toString()))

                } else {
                    if (c != ' ') {
                        throw RuntimeException("Unexpected character: $c")
                    }
                    pos++
                }
            }
        }
        lexemes.add(Lexeme(LexemeType.EOF, ""))
        return lexemes
    }
}
