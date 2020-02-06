package ru.raingo.codegen

import java.io.File
import javax.annotation.processing.Messager

class AstBuilder(private val log: Messager) {
    fun build(
        className: String,
        packageName: String,
        file: File,
        fields: Map<String, String>
    ): String {
        return """
            package $packageName
            
            import ru.raingo.fiat.yar.Ast
            import ru.raingo.fiat.yar.Node
            import ru.raingo.fiat.yar.Tag
            
            class $className : Ast {
                 override val root = Node(-1, Tag.ROOT, 0)
            }
            
        """.trimIndent()
    }
}