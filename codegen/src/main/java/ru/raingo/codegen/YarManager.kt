package ru.raingo.codegen
//
//import android.content.Context
//import android.util.Log
//import android.view.ViewGroup
//import ru.raingo.fiat.R
//import java.io.BufferedReader
//import java.io.InputStreamReader
//
class YarManager(/*private val context: Context*/) {
//    val TAG = "YarManager"
//
//    fun render(template: Component): ViewGroup? {
//        //val view = ViewBuilder(context).build(template)
//
//        //return view
//    }
//
//    fun createAstNode() {
//        val path = R.raw.TODO
//        val strings = readFile(path)
//        val tokens = Lexer.tokenize(strings)
//        val root = Parser.parse(tokens)
//        val ast = Generator.generate(root)
//
//        readAst(ast)
//    }
//
////    fun createView(path: Int): ViewGroup? {
////        val strings = readFile(path)
////        val tokens = Lexer.tokenize(strings)
////        val root = Parser.parse(tokens)
////        val ast = Generator.generate(root)
////        val view = ViewBuilder(context).build(ast)
////
////        //Log.d(TAG, "\n===============\n")
////        //readAst(ast)
////
////        return view
////    }
//
//    fun readAst(root: Node) {
//        Log.d(TAG, "Node(id=${root.id}, tag=${root.tag}, nodeId=${root.parentId}, textList=${root.textList})")
//        for (node in root.nodes) {
//            readAst(node)
//        }
//    }
//
//    fun readFile(path: Int): List<String> {
//        val inputStream = context.resources.openRawResource(path)
//        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
//
//        val output = ArrayList<String>()
//        var line = reader.readLine()
//        while (line != null) {
//            output.add(line)
//            line = reader.readLine()
//        }
//        reader.close()
//
//        Log.d("Yar", "strings $output")
//        return output
//    }
//
////    fun readFile(path: String): List<String> {
////        val inputStream = context.resources.openRawResource(path)
////        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
////
////        val output = ArrayList<String>()
////        var line = reader.readLine()
////        while (line != null) {
////            output.add(line)
////            line = reader.readLine()
////        }
////        reader.close()
////
////        Log.d("Yar", "strings $output")
////        return output
////    }
}