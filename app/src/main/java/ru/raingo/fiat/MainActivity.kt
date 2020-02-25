package ru.raingo.fiat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.raingo.fiat.views.YardIndex


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = YardIndex(this, "sdd").bind(this).build()
        setContentView(view)
    }
}
