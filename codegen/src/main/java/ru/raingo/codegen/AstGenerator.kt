package ru.raingo.codegen

import com.google.auto.service.AutoService
import ru.raingo.annotation.Template
import java.io.File
import java.io.IOException
import java.io.Writer
import java.net.URI
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation


@AutoService(Processor::class) // For registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(AstGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class AstGenerator : AbstractProcessor(){
    val pathName = "path()"

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Template::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }


    override fun process(annotations: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        if (annotations == null || annotations.isEmpty()) return false

        roundEnvironment?.getElementsAnnotatedWith(Template::class.java)
            ?.forEach {
                val log = processingEnv.messager
                val el = processingEnv.elementUtils

                var path: String? = null

                for (i in el.getAllAnnotationMirrors(it)) {
                    for (e in i.elementValues) {
                        if (e.key.toString() == pathName) path = e.value.toString()
                    }
                }

                if (path == null) {
                    log.printMessage(Diagnostic.Kind.ERROR, "Не указан путь")
                    return false
                }

                //TODO попробовать получать путь отсюда:
                //log.printMessage(Diagnostic.Kind.WARNING, "this.classLoader ${this::class.java.protectionDomain.codeSource.location.path}")

                val pp = findRootPath()
                log.printMessage(Diagnostic.Kind.WARNING, "pp: $pp")













                log.printMessage(Diagnostic.Kind.WARNING, "processingEnv.options: ${processingEnv.options}")
                //log.printMessage(Diagnostic.Kind.WARNING, "contextClassLoader: ${Thread.currentThread().contextClassLoader}")
                //log.printMessage(Diagnostic.Kind.WARNING, "contextClassLoader.parent: ${Thread.currentThread().contextClassLoader.parent}")


                val file = File("/home/raingo/AndroidStudioProjects/Fiat/app/src/main/java/ru/raingo/fiat/views")
                log.printMessage(Diagnostic.Kind.WARNING, "file.absoluteFile: ${file.absoluteFile}")
                log.printMessage(Diagnostic.Kind.WARNING, "file.canonicalPath: ${file.canonicalPath}")
                log.printMessage(Diagnostic.Kind.WARNING, "file.isDirectory: ${file.isDirectory}")
                log.printMessage(Diagnostic.Kind.WARNING, "file.canRead(): ${file.canRead()}")
                log.printMessage(Diagnostic.Kind.WARNING, "file.listFiles(): ${file.listFiles()}")

                val fileSeparator = System.getProperty("file.separator")
                val absoluteFilePth = "app" + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "java" + fileSeparator +
                        "ru"  + fileSeparator + "raingo"  + fileSeparator + "fiat"  + fileSeparator + "views"
                val file1 = File(absoluteFilePth)
                log.printMessage(Diagnostic.Kind.WARNING, "file1.absoluteFile: ${file1.absoluteFile}")
                log.printMessage(Diagnostic.Kind.WARNING, "file1.canonicalPath: ${file1.canonicalPath}")
                log.printMessage(Diagnostic.Kind.WARNING, "file1.isDirectory: ${file1.isDirectory}")
                log.printMessage(Diagnostic.Kind.WARNING, "file1.canRead(): ${file1.canRead()}")
                log.printMessage(Diagnostic.Kind.WARNING, "file1.listFiles(): ${file1.listFiles()}")


                val fieldName = it.simpleName.toString()
                val pack = processingEnv.elementUtils.getPackageOf(it).toString()
                generateAst(fieldName, pack, path)
            }

        return true
    }

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

    private fun generateAst(className: String, pack: String, path: String){
        val fileName = "Yard$className"
        val fileContent = AstBuilder(fileName, pack, path).getContent()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val file = File(kaptKotlinGeneratedDir, "$fileName.kt")

        file.writeText(fileContent)
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}