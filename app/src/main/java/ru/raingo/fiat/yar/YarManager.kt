package ru.raingo.fiat.yar

import android.content.Context
import android.util.Log
import android.view.View
import java.io.BufferedReader
import java.io.InputStreamReader

class YarManager(private val context: Context) {
    fun createView(path: Int): View {
        val strings = readFile(path)
        Lexer.tokenize(strings)
        return View(context)
    }

    fun readFile(path: Int): List<String> {
        val inputStream = context.resources.openRawResource(path)
        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

        val output = ArrayList<String>()
        var line = reader.readLine()
        while (line != null) {
            output.add(line)
            line = reader.readLine()
        }
        reader.close()

        Log.d("Yar", "strings $output")
        return output
    }
}