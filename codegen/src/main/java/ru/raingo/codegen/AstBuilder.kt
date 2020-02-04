package ru.raingo.codegen

class AstBuilder(val className: String, val packageName: String, path: String) {
    fun getContent(): String {
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