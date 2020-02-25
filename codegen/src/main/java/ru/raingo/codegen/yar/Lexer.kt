package ru.raingo.codegen.yar

enum class Lexeme(val index: Int) {
    //символы
    LAB(0), RAB(1), LCB(2),

    //теги
    TAG(3),

    //числа и строки
    STR(4);
}

val PATTERN = mapOf(
    "<" to Lexeme.LAB,      //left angle bracket
    ">" to Lexeme.RAB,      //right angle bracket
    "</" to Lexeme.LCB,     //left close bracket

    //TAGS
    "lay" to Lexeme.TAG,
    "text" to Lexeme.TAG
)

class Token (
    val type: Lexeme,
    val value: String? = null
) {
    fun getLexemeIndex(): Int = type.index
}

object Lexer {
    const val TAG = "YarLexer"

    fun tokenize(strings: List<String>): List<Token> {
        val tokens = mutableListOf<Token>()

        var line = 1
        var stringBuffer = ""
        for (string in strings) {
            var index = 0
            while (true) {
                val (lexeme, pattern) = getLexemeAndPattern(string, index, line) ?: Pair(null, null)
                if (lexeme != null && pattern != null) {
                    addStringToken(tokens, stringBuffer)
                    stringBuffer = ""

                    tokens.add(createLexeme(lexeme, pattern))
                    index += pattern.length - 1
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
            //Log.d(TAG, "token ${token.type}: ${token.value}")
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

    fun createLexeme(lexeme: Lexeme, pattern: String): Token {
        val tokenValue = if (lexeme == Lexeme.TAG) pattern else null

        return Token(lexeme, tokenValue)
    }

    fun getLexemeAndPattern(string: String, index: Int, line: Int): Pair<Lexeme?, String?>? {
        val buffer = mutableListOf<Pair<Lexeme?, String?>>()

        for ((pattern, lexeme) in PATTERN) {
            var posEnd = index
            while (true) {
                if (posEnd >= string.length) break
                val targetString = string.subSequence(index, posEnd + 1)

                if (pattern == targetString) {
                    //Log.d(TAG, "$line,${TODO+1} string $targetString, pattern $pattern ADD TO BUFFER")
                    buffer.add(Pair(lexeme, pattern))
                    break
                }

                val patternPart = pattern.subSequence(0, posEnd + 1 - index)
                //Log.d(TAG, "$line,${TODO+1} string $targetString, pattern $patternPart")

                if (patternPart == targetString) {
                    posEnd++
                    continue
                }

                break
            }
        }
        //Log.d(TAG, " \r\n")

        return buffer.maxBy { it.second?.length ?: 0 }
    }
}