package ru.raingo.codegen

import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementScanner8

class AstVisitor(env: ProcessingEnvironment, private val element: TypeElement ) : ElementScanner8<Void, Void>() {
    val log: Messager = env.messager
    val filer: Filer = env.filer
    val utils = env.elementUtils
}