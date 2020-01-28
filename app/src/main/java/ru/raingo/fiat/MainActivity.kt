package ru.raingo.fiat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.raingo.annotation.Template
import ru.raingo.fiat.yar.Ast
import ru.raingo.fiat.yar.YarManager

class MainActivity : AppCompatActivity() {
    @Template(R.raw.index)
    private lateinit var template: Ast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = YarManager(this).render(template)
        setContentView(view)
    }
}
