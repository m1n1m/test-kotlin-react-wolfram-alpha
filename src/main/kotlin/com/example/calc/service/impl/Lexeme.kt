package com.example.calc.service.impl

class Lexeme {
    var type: LexemeType
    var value: String

    constructor(type: LexemeType, value: String) {
        this.type = type
        this.value = value
    }

    constructor(type: LexemeType, value: Char) {
        this.type = type
        this.value = value.toString()
    }

    override fun toString(): String {
        return "Lexeme{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}'
    }
}