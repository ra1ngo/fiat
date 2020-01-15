package ru.raingo.fiat.yar

import android.util.Log

enum class Token{
    LAB, RAB, CLAB
}

val SYMBOLS = mapOf(
    "<" to Token.LAB,   //left angle bracket
    ">" to Token.RAB//,   //right angle bracket
    //"</" to Token.CLAB  //close left angle bracket
)

object Lexer {
    fun tokenize(strings: List<String>): List<Token> {
        val tokens = mutableListOf<Token>()

        for (string in strings) {
            for (char in string) {
                for ((s, t) in SYMBOLS) {
                    if (s == char.toString()) tokens.add(t)
                }
            }
        }

        Log.d("Yar", "tokens $tokens")
        return tokens
    }
}