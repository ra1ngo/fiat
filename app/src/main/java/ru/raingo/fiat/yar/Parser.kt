package ru.raingo.fiat.yar

enum class State {
    START,                  //начало парсинга (получение первого символа)
    START_TAG,              //получен символ "<", ждем Токен с названием тэга
    TAG_OPENING,            //начат процесс формирования открывающего тэга, получено название тэга, ожидается свойство тэга или символ ">"
    TAG_OPENING_RECEIVED,   //получен сивмол ">" после открывающего тэга, закончено формирование открывающего тэга, ожидается либо следующий открывающий тэг, либо текст
    TEXT_RECEIVED,          //получен текст, ожидается либо другой текст, либо открывающий тэг, либо закрывающий тэг, либо токены, которые должны быть строкой ("Hello <Ilya>!" - это все строка)
    //CORRECTION_TEXT,        //если после текста получены какие-либо токены, кроме "<" или "</" - то они присоединяются к предыдущему тексту
    WAIT_CLOSED_TAG,        //получен символ "</", ждем Токен с названием тэга
    TAG_CLOSED,             //начат процесс формирования закрывающего тэга, получено название тэга, сравнивается название открывающего и закрывающего тэга, ожидается символ ">"
    //END_TAG,                //тэг сформирован, создаем ноду тэга
    END                     //конец парсинга (получен последний символ)
}

//таблица переходов
val fsm = listOf(
    //       LAB       RAB      CLAB       TAG       STR
    mapOf("a" to 1, "b" to 0, "c" to 0, "d" to 0, "e" to 0),    //START
    mapOf("a" to 0, "b" to 0, "c" to 0, "d" to 1, "e" to 0),    //START_TAG
    mapOf("a" to 0, "b" to 1, "c" to 0, "d" to 0, "e" to 0),    //TAG_OPENING
    mapOf("a" to 1, "b" to 0, "c" to 0, "d" to 1, "e" to 0),    //TAG_OPENING_RECEIVED
    mapOf("a" to 0, "b" to 0, "c" to 1, "d" to 0, "e" to 0),    //TEXT_RECEIVED
    //mapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0, "e" to 0),    //CORRECTION_TEXT
    mapOf("a" to 0, "b" to 0, "c" to 0, "d" to 1, "e" to 0),    //WAIT_CLOSED_TAG
    mapOf("a" to 0, "b" to 1, "c" to 0, "d" to 0, "e" to 0),    //TAG_CLOSED
    //mapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0, "e" to 0),    //END_TAG
    mapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0, "e" to 0)     //END
)

object Parser {
    const val TAG = "YarParser"

    fun parse(tokens: List<Token>) {

    }
}