package ru.raingo.fiat.yar

import android.util.Log

enum class Lexeme{
    //символы
    LAB, RAB, CLAB,

    //теги
    CLAY, LAY, TXT,

    //числа и строки
    NUM, STR
}

val SYMBOLS = mapOf(
    "<" to Lexeme.LAB,      //left angle bracket
    ">" to Lexeme.RAB,      //right angle bracket
    "</" to Lexeme.CLAB,    //close left angle bracket
    "ConstraintLayout" to Lexeme.CLAY,
    "Lay" to Lexeme.LAY,
    "text" to Lexeme.TXT
)

class Token (
    type: Lexeme,
    value: String?
)

object Lexer {
    const val TAG = "YarLexer"

    fun tokenize(strings: List<String>): List<Lexeme> {
        val tokens = mutableListOf<Lexeme>()

        var line = 1
        for (string in strings) {
            var index = 0
            while (true) {

                val lexeme = getLexeme(string, index, line)
                if (lexeme != null) {
                    tokens.add(lexeme)
                    index += SYMBOLS.filterValues { it == lexeme }.keys.first().length - 1
                }

                if (index >= string.length - 1) break
                index++
            }

            line++
        }

        Log.d(TAG, "tokens $tokens")
        return tokens
    }

    fun getLexeme(string: String, index: Int, line: Int): Lexeme? {
        val buffer = mutableListOf<Lexeme>()

        for ((pattern, lexeme) in SYMBOLS) {
            var posEnd = index
            while (true) {
                if (posEnd >= string.length) break
                val targetString = string.subSequence(index, posEnd + 1)

                if (pattern == targetString) {
                    Log.d(TAG, "$line,${index+1} string $targetString, pattern $pattern ADD TO BUFFER ")
                    buffer.add(lexeme)
                    break
                }

                val patternPart = pattern.subSequence(0, posEnd + 1 - index)
                Log.d(TAG, "$line,${index+1} string $targetString, pattern $patternPart")

                if (patternPart == targetString) {
                    posEnd++
                    continue
                }

                break
            }
        }
        Log.d(TAG, " \r\n")

        return buffer.max()
    }
}