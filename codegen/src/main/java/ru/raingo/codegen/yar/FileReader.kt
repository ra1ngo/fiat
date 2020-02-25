package ru.raingo.codegen.yar

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.annotation.processing.Messager
import javax.tools.Diagnostic

object FileReader {
    fun readFile(log: Messager, file: File): List<String> {
        val inputStream = file.inputStream()
        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

        val output = ArrayList<String>()
        var line = reader.readLine()
        while (line != null) {
            output.add(line)
            line = reader.readLine()
        }
        reader.close()

        //log.printMessage(Diagnostic.Kind.ERROR, "strings $output")
        return output
    }
}