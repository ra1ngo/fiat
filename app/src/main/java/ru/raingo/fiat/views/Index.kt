package ru.raingo.fiat.views

import android.util.Log
import ru.raingo.annotation.Template
import ru.raingo.fiat.MainActivity

@Template("ru.raingo.fiat.views.index.yar")
abstract class Index (
    open val act: MainActivity,
    open val str: String = "не поддерживаются значения по умолчанию в конструкторе"
) {
    val i: String = "тут все норм"

    fun log() {
        Log.d("Yar", str)
    }
}