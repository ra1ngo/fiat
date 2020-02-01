package ru.raingo.fiat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.raingo.annotation.Template
import ru.raingo.fiat.yar.Ast
import ru.raingo.fiat.yar.YarManager

class MainActivity : AppCompatActivity() {
    @Template(R.raw.index)
    //private lateinit var template: Ast
    class Index

    val str = "aaaaaaaaaaaa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Yar", str)

        val view = YarManager(this).render(Yard_Index())
        setContentView(view)
    }
}
