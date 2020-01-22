package ru.raingo.fiat.yar

import android.util.Log

enum class State(val index: Int) {
    START(0),                  //начало парсинга (получение первого символа)
    START_TAG(1),              //получен символ "<", ждем Токен с названием тэга
    TAG_OPENING(2),            //начат процесс формирования открывающего тэга, получено название тэга, ожидается свойство тэга или символ ">"
    TAG_OPENING_RECEIVED(3),   //получен сивмол ">" после открывающего тэга, закончено формирование открывающего тэга, ожидается либо следующий открывающий тэг, либо текст
    TEXT_RECEIVED(4),          //получен текст, ожидается либо другой текст, либо открывающий тэг, либо закрывающий тэг, либо токены, которые должны быть строкой ("Hello <Ilya>!" - это все строка)
    //CORRECTION_TEXT(5),        //если после текста получены какие-либо токены, кроме "<" или "</" - то они присоединяются к предыдущему тексту
    WAIT_CLOSED_TAG(6),        //получен символ "</", ждем Токен с названием тэга
    TAG_CLOSED(7),             //начат процесс формирования закрывающего тэга, получено название тэга, сравнивается название открывающего и закрывающего тэга, ожидается символ ">"
    //END_TAG(8),                //тэг сформирован, создаем ноду тэга
    END(9);                     //конец парсинга (получен последний символ)

    companion object {
        private val map = values().associateBy(State::index)
        fun fromIndex(index: Int) = map[index]
    }
}

//таблица переходов
val transitionTable = listOf(
    //      LAB  RAB  LCB  TAG  STR
    listOf(  1,   0,   0,   0,   0  ),  //START
    listOf(  0,   0,   0,   2,   0  ),  //START_TAG
    listOf(  0,   3,   0,   0,   0  ),  //TAG_OPENING
    listOf(  0,   0,   6,   0,   4  ),  //TAG_OPENING_RECEIVED
    listOf(  0,   0,   6,   0,   4  ),  //TEXT_RECEIVED
//  listOf(  0,   0,   0,   0,   0  ),  //CORRECTION_TEXT
    listOf(  0,   0,   0,   7,   0  ),  //WAIT_CLOSED_TAG
    listOf(  0,   9,   0,   0,   0  ),  //TAG_CLOSED
//  listOf(  0,   0,   0,   0,   0  ),  //END_TAG
    listOf(  0,   0,   0,   0,   0  )   //END
)

object Parser {
    const val TAG = "YarParser"
    //val fsm = FiniteStateMachine()
    var state = State.START
    var currentToken: Token? = null

    fun parse(tokens: List<Token>) {
        Log.d(TAG, "\n===============\n")

        for (token in tokens) {
            currentToken = token
            val action = actions[state.index]

            action()

            val lexemeIndex: Int = token.getLexemeIndex() ?: return error()
            val stateIndex: Int = transitionTable[state.index][lexemeIndex]
            if (stateIndex == 0) { error() }
            state = State.fromIndex(stateIndex) ?: return error()
        }
    }

    fun error() {
        Log.d(TAG, "ошибка парсинга")
    }

    //ACTIONS
    val actions = listOf(
        ::onStart, ::onStartTag, ::onTagOpening, ::onTagOpeningReceived, ::onTextReceived,
        ::onCorrectionText, ::onWaitClosedTag, ::onTagClosed, ::onEndTag, ::onEnd
    )

    fun onStart() {
        Log.d(TAG, "onStart")
    }

    fun onStartTag() {
        Log.d(TAG, "onStartTag")
    }

    fun onTagOpening() {
        Log.d(TAG, "onTagOpening")
    }

    fun onTagOpeningReceived() {
        Log.d(TAG, "onTagOpeningReceived")
    }

    fun onTextReceived() {
        Log.d(TAG, "onTextReceived")
    }

    fun onCorrectionText() {
        Log.d(TAG, "onCorrectionText")
    }

    fun onWaitClosedTag() {
        Log.d(TAG, "onWaitClosedTag")
    }

    fun onTagClosed() {
        Log.d(TAG, "onTagClosed")
    }

    fun onEndTag() {
        Log.d(TAG, "onEndTag")
    }

    fun onEnd() {
        Log.d(TAG, "onEnd")
    }
}

//class FiniteStateMachine {
//    fun action() {}
//}