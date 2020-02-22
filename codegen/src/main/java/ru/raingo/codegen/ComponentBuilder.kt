package ru.raingo.codegen

import ru.raingo.codegen.yar.*
import java.io.File
import javax.annotation.processing.Messager
import javax.tools.Diagnostic

class ComponentBuilder(private val log: Messager) {
    fun build(
        className: String,
        packageName: String,
        file: File,
        fields: Map<String, String>
    ): String {
        val strings = FileReader.readFile(log, file)
        val tokens = Lexer.tokenize(strings)
        val root = Parser.parse(tokens)
        val component = Generator.generate(root)

        readAst(root)

        //val component = ClassBuilder.build(ast)
//        return """
//            package $packageName
//
//            import ru.raingo.codegen.yar.Ast
//            import ru.raingo.codegen.yar.Node
//            import ru.raingo.codegen.yar.Tag
//
//            class $className : Ast {
//                 override val root = Node(-1, Tag.ROOT, 0)
//            }
//
//        """.trimIndent()

        return component
    }

    fun readAst(root: Node) {
        log.printMessage(Diagnostic.Kind.ERROR, "Node(id=${root.id}, tag=${root.tag}, nodeId=${root.parentId}, textList=${root.textList})")
        for (node in root.nodes) {
            readAst(node)
        }
    }
}