package ru.raingo.codegen.yar

enum class State(val index: Int) {
    START(0),                  //начало парсинга (получение первого символа)
    START_TAG(1),              //получен символ "<", ждем Токен с названием тэга
    TAG_OPENING(2),            //начат процесс формирования открывающего тэга, получено название тэга, ожидается свойство тэга или символ ">"
    TAG_OPENING_RECEIVED(3),   //получен сивмол ">" после открывающего тэга, закончено формирование открывающего тэга, ожидается либо следующий открывающий тэг, либо текст
    TEXT_RECEIVED(4),          //получен текст, ожидается либо другой текст, либо открывающий тэг, либо закрывающий тэг, либо токены, которые должны быть строкой ("Hello <Ilya>!" - это все строка)
    CORRECTION_TEXT(5),        //если после текста получены какие-либо токены, кроме "<" или "</" - то они присоединяются к предыдущему тексту
    WAIT_CLOSED_TAG(6),        //получен символ "</", ждем Токен с названием тэга
    TAG_CLOSED(7),             //начат процесс формирования закрывающего тэга, получено название тэга, сравнивается название открывающего и закрывающего тэга, ожидается символ ">"
    END_TAG(8),                //тэг сформирован, создаем ноду тэга
    END(9);                    //конец парсинга (получен последний символ)

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
    listOf(  1,   0,   6,   0,   4  ),  //TAG_OPENING_RECEIVED
    listOf(  1,   0,   6,   0,   4  ),  //TEXT_RECEIVED
    listOf(  0,   0,   0,   0,   0  ),  //CORRECTION_TEXT
    listOf(  0,   0,   0,   7,   0  ),  //WAIT_CLOSED_TAG
    listOf(  0,   8,   0,   0,   0  ),  //TAG_CLOSED
    listOf(  1,   0,   6,   0,   4  ),  //END_TAG
    listOf(  0,   0,   0,   0,   0  )   //END
)

object Parser {
    const val TAG = "YarParser"
    //val fsm = FiniteStateMachine()
    var state = State.START
    var currentToken: Token? = null

    var counterId = 0
    val root = Node(counterId, Tag.ROOT, -1)
    val stack: MutableList<Node> = mutableListOf(root)
    var error = false

    fun parse(tokens: List<Token>): Node {
        actions[state.index]()  //больше вызываться этот хендлер не будет, 0 - будет ошибкой/

        for (token in tokens) {
            if (error) break

            currentToken = token

            val lexemeIndex: Int = token.getLexemeIndex()
            //Log.d(TAG, "state $state, state.TODO ${state.TODO}, lexemeIndex $lexemeIndex")
            val stateIndex: Int = transitionTable[state.index][lexemeIndex]
            if (stateIndex == 0) {
                //Log.d(TAG, "ошибка stateIndex == 0")
                break
            }

            val nextState = State.fromIndex(stateIndex)
            if (nextState == null) {
                //Log.d(TAG, "ошибка state is null")
                break
            }
            state = State.fromIndex(stateIndex)!!

            val action = actions[state.index]
            action()
        }

        actions[State.END.index]()

        return root
    }

    fun error() {
        error = true
        //Log.d(TAG, "error $counterId\n$root\n$stack")
    }

    //ACTIONS
    val actions = listOf(
        ::onStart, ::onStartTag, ::onTagOpening, ::onTagOpeningReceived, ::onTextReceived,
        ::onCorrectionText, ::onWaitClosedTag, ::onTagClosed, ::onEndTag, ::onEnd
    )

    fun onStart() {
        //Log.d(TAG, "\n===============\n")
        //Log.d(TAG, "onStart")
    }

    fun onStartTag() {
        //Log.d(TAG, "onStartTag")
    }

    fun onTagOpening() {
        //Log.d(TAG, "onTagOpening")

        val tag = currentToken?.value?.let { Tag.fromString(it) } ?: return error()

        val currentNode = Node(++counterId, tag, -1)
        stack.add(currentNode)
    }

    fun onTagOpeningReceived() {
        //Log.d(TAG, "onTagOpeningReceived")
    }

    fun onTextReceived() {
        //Log.d(TAG, "onTextReceived")
        currentToken?.value?.let { stack.last().textList.add(it) }
    }

    fun onCorrectionText() {
        //Log.d(TAG, "onCorrectionText")
    }

    fun onWaitClosedTag() {
        //Log.d(TAG, "onWaitClosedTag")
    }

    fun onTagClosed() {
        //Log.d(TAG, "onTagClosed")
        val tag = currentToken?.value
        if (tag == null || tag != stack.last().tag.pattern) return error()
    }

    fun onEndTag() {
        //Log.d(TAG, "onEndTag")
        val receivedNode = stack.removeAt(stack.size - 1)
        receivedNode.parentId = stack.last().id
        stack.last().nodes.add(receivedNode)
    }

    fun onEnd() {
        //Log.d(TAG, "onEnd")
        if (stack.size != 1) return error()
        stack.remove(root)
        if (stack.size != 0) return error()
    }
}

//class FiniteStateMachine {
//    fun action() {}
//}