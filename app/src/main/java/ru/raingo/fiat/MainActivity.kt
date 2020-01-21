package ru.raingo.fiat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.raingo.fiat.yar.YarManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        render(R.raw.index)
    }

    fun render(path: Int) {
        val view = YarManager(this).createView(path)
        setContentView(view)
    }
}
