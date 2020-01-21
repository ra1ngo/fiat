package ru.raingo.fiat.yar

import android.util.Log

enum class Lexeme {
    //символы
    LAB, RAB, CLAB,

    //теги
    TAG,
    CLAY, LAY, TXT,

    //числа и строки
    STR
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
    val type: Lexeme,
    val value: String? = null
)

object Lexer {
    const val TAG = "YarLexer"

    fun tokenize(strings: List<String>): List<Token> {
        val tokens = mutableListOf<Token>()

        var line = 1
        var stringBuffer = ""
        for (string in strings) {
            var index = 0
            while (true) {
                val lexeme = getLexeme(string, index, line)
                if (lexeme != null) {
                    addStringToken(tokens, stringBuffer)
                    stringBuffer = ""

                    tokens.add(createLexeme(lexeme))
                    index += SYMBOLS.filterValues { it == lexeme }.keys.first().length - 1
                } else {
                    stringBuffer += string[index]
                }

                if (index >= string.length - 1) break
                index++
            }

            line++
        }


        logStringLexeme(tokens)
        return tokens
    }

    fun logStringLexeme(tokens: List<Token>) {
        for (token in tokens) {
            Log.d(TAG, "token ${token.type}")
            if (token.type == Lexeme.STR) Log.d(TAG, "String Lexeme: ${token.value}")
        }
    }

    //Нужно продумать экранирование для символов "<" и "</"
    //Например, "Hello <Ilya>!" - это все строка
    //Если эти лексемы экранированы, то соответствующие токены не создаются, а присоединяются к stringBuffer
    fun addStringToken(tokens: MutableList<Token>, stringBuffer: String) {
        if (stringBuffer == "" || stringBuffer.trim() == "") return

        val token = Token(Lexeme.STR, stringBuffer)
        tokens.add(token)
        //return tokens
    }

    fun createLexeme(type: Lexeme): Token {
        return Token(type)
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