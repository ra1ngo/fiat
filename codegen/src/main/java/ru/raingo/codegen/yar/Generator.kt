package ru.raingo.codegen.yar

import com.squareup.kotlinpoet.*
import javax.annotation.processing.Messager
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.tools.Diagnostic
import javax.tools.JavaFileObject

class Generator(
    private val log: Messager,
    private val prefixClassName: String
) {
    fun generate(
        root: Node,
        className: String,
        packageName: String,
        fields: Map<String, String>
    ): FileSpec {
        val clazz = TypeSpec.classBuilder("$prefixClassName$className")
        formConstructor(clazz, fields, packageName, className)
        //formRoot(clazz)
        ViewBuilder(log).build(root, clazz, "$prefixClassName$className", packageName)

        val file = FileSpec.builder(packageName, className)
        file.addType(clazz.build())
        //readFile(file.build().toJavaFileObject())

        return file.build()
    }

//    private fun formRoot(clazz: TypeSpec.Builder) {
//        val classRoot = ClassName("android.view", "ViewGroup")
//        val propertyRoot = PropertySpec.builder("root", classRoot)
//            .mutable()
//            .addModifiers(KModifier.PRIVATE, KModifier.LATEINIT)
//            .build()
//
//        clazz.addProperty(propertyRoot)
//    }

    private fun formConstructor(
        clazz: TypeSpec.Builder,
        fields: Map<String, String>,
        packageName: String,
        classNameParent: String
    ) {
        //log.printMessage(Diagnostic.Kind.ERROR, "fields $fields")
        val constructorFields = importConstructorFields(fields)

        val constructor = FunSpec.constructorBuilder()
        for (field in constructorFields) {
            constructor.addParameter(field.key, field.value)
            clazz.addProperty(
                PropertySpec.builder(field.key, field.value)
                    .addModifiers(KModifier.OVERRIDE)
                    .initializer(field.key)
                    .build()
            )
            clazz.addSuperclassConstructorParameter(field.key, field.value)
        }

        clazz.primaryConstructor(constructor.build())
        clazz.superclass(ClassName(packageName, classNameParent))
    }

    private fun importConstructorFields(fields: Map<String, String>): HashMap<String, ClassName> {
        val constructorFields = linkedMapOf<String, ClassName>()

        for ((key, field) in fields) {
            val classNameImport = ClassName(field.substringBeforeLast("."), field.substringAfterLast("."))
            constructorFields[key] = classNameImport
        }

        return constructorFields
    }

    fun readFile(file: JavaFileObject): List<String> {
        val inputStream = file.openInputStream()
        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

        val output = ArrayList<String>()
        var line = reader.readLine()
        log.printMessage(Diagnostic.Kind.ERROR, "////////////////")
        while (line != null) {
            log.printMessage(Diagnostic.Kind.ERROR, line)
            output.add(line)
            line = reader.readLine()
        }
        log.printMessage(Diagnostic.Kind.ERROR, "////////////////")
        reader.close()

        return output
    }
}