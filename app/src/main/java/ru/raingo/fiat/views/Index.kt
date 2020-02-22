package ru.raingo.fiat.views

import ru.raingo.annotation.Template
import ru.raingo.fiat.MainActivity

@Template("ru.raingo.fiat.views.index.yar")
abstract class Index (
    val act: MainActivity,
    val str: String = "не поддерживаются значения по умолчанию в конструкторе"
) {
    val i: String = "тут все норм"
}