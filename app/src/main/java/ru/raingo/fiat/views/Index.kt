package ru.raingo.fiat.views

import ru.raingo.annotation.Template
import ru.raingo.fiat.MainActivity

@Template("ru.raingo.fiat.views.index.yar")
abstract class Index (
    val act: MainActivity,
    val str: String = "ssssss11111111111s"
) {
    val i: String = "ii"
}