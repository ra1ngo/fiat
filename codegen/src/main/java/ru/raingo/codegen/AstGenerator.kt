package ru.raingo.codegen

import com.google.auto.service.AutoService
import ru.raingo.annotation.Template
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.util.ElementScanner8
import javax.tools.Diagnostic


@AutoService(Processor::class) // For registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(AstGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class AstGenerator : AbstractProcessor(){
    private lateinit var log: Messager

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Template::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }


    override fun process(annotations: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        if (annotations == null || annotations.isEmpty()) return false
        log =  processingEnv.messager

        roundEnvironment?.getElementsAnnotatedWith(Template::class.java)
            ?.forEach {
                if (!it.kind.isClass || !it.modifiers.contains(Modifier.ABSTRACT)) {
                    log.printMessage(Diagnostic.Kind.ERROR, "Помечанный аннотацией должен быть абстрактным классом")
                    return false
                }

                val el = processingEnv.elementUtils

                var path: String? = null

                for (i in el.getAllAnnotationMirrors(it)) {
                    for (e in i.elementValues) {
                        if (e.key.toString() == PATH_ATTR_NAME) path = e.value.toString()
                    }
                }

                if (path == null) {
                    log.printMessage(Diagnostic.Kind.ERROR, "Не указан путь")
                    return false
                }

                val fields = linkedMapOf<String, String>()
                for (a in it.enclosedElements) {
                    if (a.kind != ElementKind.CONSTRUCTOR) continue

                    a.accept(object : ElementScanner8<Void, Void?>() {
                        override fun visitVariable(field: VariableElement?, p1: Void?): Void? {
                            //так можно узнать значение только полей, но не параметров
                            //скорее всего это невозможно, если верить гуглу
                            //log.printMessage(Diagnostic.Kind.WARNING, "constantValue ${field?.constantValue}")

                            fields[field.toString()] = field?.asType().toString()

                            return super.visitVariable(field, p1)
                        }
                    }, null)
                }

                val fieldName = it.simpleName.toString()
                val pack = el.getPackageOf(it).toString()
                generateAst(fieldName, pack, path, fields)
            }

        return true
    }

    private fun generateAst(
        className: String,
        pack: String,
        path: String,
        fields: Map<String, String>
    ) {
        val fileName = "Yard$className"
        val fileTemplate = findFile(path) ?: return

        val fileContent = AstBuilder(log).build(fileName, pack, fileTemplate, fields)

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val file = File(kaptKotlinGeneratedDir, "$fileName.kt")
        file.writeText(fileContent)
    }

    private fun findFile(pathAttr: String): File? {
        val pathAttrPure = pathAttr.replace("\"", "")
        if (pathAttrPure.substringAfterLast(".") != "yar") {
            log.printMessage(Diagnostic.Kind.WARNING, "Файл должен быть с расширением \".yar\"")
        }

        val pathRoot = findRootPath()
        val sep = System.getProperty("file.separator")
        val pathAttrCorrect = pathAttrPure.replace(".", sep).reversed().replaceFirst(sep, ".").reversed()

        var path = pathRoot.toString() + sep + "src" + sep + "main" + sep + "java" + sep + pathAttrCorrect
        var file = File(path)
        if (file.exists()) return file

        path = pathRoot?.parent.toString() + sep + pathAttrCorrect
        file = File(path)
        if (file.exists()) return file

        log.printMessage(Diagnostic.Kind.ERROR, "Не найден файл по пути: $path")
        return null
    }

    //TODO попробовать получать путь отсюда:
    //log.printMessage(Diagnostic.Kind.WARNING, "this.classLoader ${this::class.java.protectionDomain.codeSource.location.path}")
    //TODO еще попробовать брать из градл переменной
    private fun findRootPath(): Path? {
        try {
            val tmp = processingEnv.filer.createSourceFile("tmp" + System.currentTimeMillis())
            val projectPath = Paths.get(tmp.toUri()).parent.parent.parent.parent.parent.parent
            tmp.delete()

            return projectPath
        } catch (e: IOException) {
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Unable to determine source file path!")
        }

        return null
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        const val PATH_ATTR_NAME = "path()"
    }
}