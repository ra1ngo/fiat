package ru.raingo.fiat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.raingo.annotation.Template
import ru.raingo.fiat.views.YardIndex
import ru.raingo.fiat.yar.Ast
import ru.raingo.fiat.yar.YarManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = YarManager(this).render(YardIndex())
        setContentView(view)
    }
}
