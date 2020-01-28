package ru.raingo.fiat.yar

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import java.io.BufferedReader
import java.io.InputStreamReader

class YarManager(private val context: Context) {
    val TAG = "YarManager"

    fun render(template: Ast): ViewGroup? {
        val view = ViewBuilder(context).build(template)

        return view
    }

    fun createView(path: Int): ViewGroup? {
        val strings = readFile(path)
        val tokens = Lexer.tokenize(strings)
        val root = Parser.parse(tokens)
        val ast = Generator.generate(root)
        val view = ViewBuilder(context).build(ast)

        //Log.d(TAG, "\n===============\n")
        //readAst(ast)

        return view
    }

    fun readAst(root: Node) {
        Log.d(TAG, "Node(id=${root.id}, tag=${root.tag}, nodeId=${root.nodeId}, textList=${root.textList})")
        for (node in root.nodes) {
            readAst(node)
        }
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