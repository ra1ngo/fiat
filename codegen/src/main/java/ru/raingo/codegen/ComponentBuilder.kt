package ru.raingo.codegen

import com.squareup.kotlinpoet.FileSpec
import ru.raingo.codegen.yar.*
import java.io.File
import javax.annotation.processing.Messager
import javax.tools.Diagnostic

class ComponentBuilder(private val log: Messager) {
    fun build(
        prefixClassName: String,
        className: String,
        packageName: String,
        file: File,
        fields: Map<String, String>
    ): FileSpec {
        val strings = FileReader.readFile(log, file)
        val tokens = Lexer.tokenize(strings)
        val root = Parser.parse(tokens)
        val component = Generator(log, prefixClassName).generate(root, className, packageName, fields)

        //readAst(root)

        return component
    }

    fun readAst(root: Node) {
        log.printMessage(Diagnostic.Kind.ERROR, "Node(id=${root.id}, tag=${root.tag}, nodeId=${root.parentId}, textList=${root.textList})")
        for (node in root.nodes) {
            readAst(node)
        }
    }
}