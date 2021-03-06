package ru.raingo.codegen.yar

enum class Tag(val pattern: String) {
    ROOT("root"), LAY("lay"), TEXT("text");

    companion object {
        private val map = values().associateBy(Tag::pattern)
        fun fromString(pattern: String) = map[pattern]
    }
}

data class Node(
    val id: Int,
    val tag: Tag,
    var parentId: Int?,
    val textList: MutableList<String> = mutableListOf(),
    val scriptList: MutableList<String> = mutableListOf(),
    val nodes: MutableList<Node> = mutableListOf()
)