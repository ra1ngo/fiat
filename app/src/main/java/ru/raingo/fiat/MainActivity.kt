package ru.raingo.fiat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.raingo.fiat.views.YardIndex

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = ViewBuilder(this).build(YardIndex(this))
        setContentView(view)
    }
}
