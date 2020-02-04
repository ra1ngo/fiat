package ru.raingo.codegen

import com.google.auto.service.AutoService
import ru.raingo.annotation.Template
import java.io.File
import java.io.IOException
import java.io.Writer
import java.net.URI
import java.net.URL
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
                //val element = it.enclosingElement as TypeElement
                //val visitor = AstVisitor(processingEnv, element)
                //element.accept(visitor, null)
                //visitor.generate()

                ///////////////
                val log = processingEnv.messager
                val el = processingEnv.elementUtils
                val rootElements = roundEnvironment.rootElements
                val annotatedElements = roundEnvironment.getElementsAnnotatedWith(Template::class.java)


                val filer = processingEnv.filer
                //val st = annotatedElements.first().asType().toString().replace(".", "/") + ".java"
                //val res = filer.getResource(StandardLocation.SOURCE_PATH, "", st)
                //log.printMessage(Diagnostic.Kind.WARNING, "StandardLocation.SOURCE_PATH ${StandardLocation.SOURCE_PATH}")

                var path: String? = null

                log.printMessage(Diagnostic.Kind.WARNING, "getAllAnnotationMirrors ${el.getAllAnnotationMirrors(it)}")
                for (i in el.getAllAnnotationMirrors(it)) {
                    log.printMessage(Diagnostic.Kind.WARNING, "mirror: ${i.annotationType} ${i.elementValues}")
                    for (e in i.elementValues) {
                        log.printMessage(Diagnostic.Kind.WARNING, "elementValues: ${e.key} ${e.value}")
                        if (e.key.toString() == pathName) path = e.value.toString()
                    }
                }

                if (path == null) {
                    log.printMessage(Diagnostic.Kind.ERROR, "Не указан путь")
                    return false
                }

                log.printMessage(Diagnostic.Kind.WARNING, "this.classLoader ${this::class.java.protectionDomain.codeSource.location.path}")
















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

                //log.printMessage(Diagnostic.Kind.WARNING, "asType ${it.javaClass.protectionDomain.codeSource.location.path}")
                log.printMessage(Diagnostic.Kind.WARNING, "this.classLoader ${this::pathName}")
                log.printMessage(Diagnostic.Kind.WARNING, "this.classLoader ${this::class.java.protectionDomain.codeSource.location.path}")
                val classLoader = Thread.currentThread().contextClassLoader
                log.printMessage(Diagnostic.Kind.WARNING, "classLoader ${Thread.currentThread().contextClassLoader}")
                val url: URL? = classLoader.getResource("/app/src/main/java/ru/raingo/fiat/views")
                log.printMessage(Diagnostic.Kind.WARNING, "url $url")

                val ff = File("").absolutePath
                log.printMessage(Diagnostic.Kind.WARNING, "ff $ff")

                //val f = processingEnv.filer.getResource(StandardLocation.SOURCE_PATH, "ru.raingo.fiat.views", "index.yar")
                //log.printMessage(Diagnostic.Kind.WARNING, "f: $f")
                //log.printMessage(Diagnostic.Kind.WARNING, "f.getCharContent: ${f.getCharContent(true)}")

                //val is1: InputStream = processingEnv.filer.getResource(StandardLocation.SOURCE_PATH, pkg, "Bundle.properties").openInputStream()
                //log.printMessage(Diagnostic.Kind.WARNING, "StandardLocation.CLASS_OUTPUT: ${StandardLocation.CLASS_OUTPUT}")
                //val layer = filer.getResource(StandardLocation.CLASS_OUTPUT, "", "")
                //log.printMessage(Diagnostic.Kind.WARNING, "layer: $layer")





                log.printMessage(Diagnostic.Kind.WARNING, "System ${System.getProperties()}")
                log.printMessage(Diagnostic.Kind.WARNING, "System ${System.getenv()}")


                log.printMessage(Diagnostic.Kind.WARNING, "findLayouts() ${findLayouts()}")
                ff()
                log.printMessage(Diagnostic.Kind.WARNING, "fff() ${fff()}")
                log.printMessage(Diagnostic.Kind.WARNING, "ffff() ${ffff()}")

                log.printMessage(Diagnostic.Kind.WARNING, "Template::class.java ${Template::class.java.protectionDomain.codeSource.location.path}")
                log.printMessage(Diagnostic.Kind.WARNING, "Template::class.java ${Template::class.java.protectionDomain.codeSource.location}")
                log.printMessage(Diagnostic.Kind.WARNING, "Template::class.java ${Template::class.java.protectionDomain.codeSource}")
                log.printMessage(Diagnostic.Kind.WARNING, "Template::class.java ${Template::class.java.classLoader}")
                //val pp = Template::class.java.classLoader.getResource("R.raw.index").path
                //log.printMessage(Diagnostic.Kind.WARNING, "Template::class.java $pp")

/*


                log.printMessage(Diagnostic.Kind.WARNING, "rootElements $rootElements")
                for (s in rootElements) {
                    log.printMessage(Diagnostic.Kind.WARNING, "rootElement $s")
                }

                log.printMessage(Diagnostic.Kind.WARNING, "annotatedElements $annotatedElements")
                for (q in annotatedElements) {
                    log.printMessage(Diagnostic.Kind.WARNING, "annotatedElement $q")
                    log.printMessage(Diagnostic.Kind.WARNING, "annotatedElement ${q::class.java.protectionDomain.codeSource.location.path}")
                    log.printMessage(Diagnostic.Kind.WARNING, "annotatedElement path ${q.javaClass.protectionDomain.codeSource.location.path}")
                }



                log.printMessage(Diagnostic.Kind.WARNING, "getDocComment ${el.getDocComment(it)}")
                log.printMessage(Diagnostic.Kind.WARNING, "getPackageOf ${el.getPackageOf(it)}")
                log.printMessage(Diagnostic.Kind.WARNING, "getPackageOf ${el::class.java.protectionDomain.codeSource.location.path}")
                //НЕ РОБИТ: log.printMessage(Diagnostic.Kind.WARNING, "getConstantExpression ${el.getConstantExpression(it)}")
                log.printMessage(Diagnostic.Kind.WARNING, "getAllMembers ${el.getAllMembers(annotations.first())}")
                log.printMessage(Diagnostic.Kind.WARNING, "enclosedElements ${it.enclosedElements}")
                for (a in it.enclosedElements) {
                    log.printMessage(Diagnostic.Kind.WARNING, "enclosedElement $a")
                    log.printMessage(Diagnostic.Kind.WARNING, "enclosedElement.asType() ${a.asType()}")
                }
                log.printMessage(Diagnostic.Kind.WARNING, "enclosingElement ${it.enclosingElement}")
                for (p in it.enclosingElement.enclosedElements) {
                    log.printMessage(Diagnostic.Kind.WARNING, "!!enclosingElement enclosedElements $p")










//                    if ("$p" == "str") {
//                        p.accept(object : ElementKindVisitor8<Void, Void>() {
//                            override fun defaultAction(p0: Element?, p1: Void?): Void? {
//                                log.printMessage(Diagnostic.Kind.WARNING, "defaultAction $p0")
//                                return super.defaultAction(p0, p1)
//                            }
//
//                            override fun visitVariable(field: VariableElement?, p1: Void?): Void? {
//                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.enclosingElement}")
//                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.constantValue}")
//                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.annotationMirrors}")
//                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.annotationMirrors?.first()?.elementValues}")
//                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.annotationMirrors?.first()?.annotationType}")
//
//                                return super.visitVariable(field, p1)
//                            }
//                        }, null)
//                    }













                    log.printMessage(Diagnostic.Kind.WARNING, "!!enclosingElement enclosedElements.asType() ${p.asType()}")
                    log.printMessage(Diagnostic.Kind.WARNING, "!!enclosingElement enclosedElements.asType().kind ${p.asType().kind}")
                    log.printMessage(Diagnostic.Kind.WARNING, "!!enclosingElement enclosedElements.kind ${p.kind}")
                }
                log.printMessage(Diagnostic.Kind.WARNING, "kind ${it.kind}")
                log.printMessage(Diagnostic.Kind.WARNING, "kind.isClass ${it.kind.isClass}")
                log.printMessage(Diagnostic.Kind.WARNING, "modifiers ${it.modifiers}")
                log.printMessage(Diagnostic.Kind.WARNING, "simpleName ${it.simpleName}")
                log.printMessage(Diagnostic.Kind.WARNING, "asType ${it.asType()}")
                log.printMessage(Diagnostic.Kind.WARNING, "it $it")
                log.printMessage(Diagnostic.Kind.WARNING, "el $el")
                log.printMessage(Diagnostic.Kind.WARNING, "annotations $annotations")
                log.printMessage(Diagnostic.Kind.WARNING, "annotations.first() ${annotations.first()}}")

                //el.printElements(log, it)
*/
                ///////////////

                val fieldName = it.simpleName.toString()
                val pack = processingEnv.elementUtils.getPackageOf(it).toString()
                generateAst(fieldName, pack, path)
            }

        return true
    }

    @Throws(Exception::class)
    private fun findLayouts(): String? {
        val log = processingEnv.messager

        val filer = processingEnv.filer
        val dummySourceFile = filer.createSourceFile("dummy" + System.currentTimeMillis())
        var dummySourceFilePath = dummySourceFile.toUri().toString()
        if (dummySourceFilePath.startsWith("file:")) {
            if (!dummySourceFilePath.startsWith("file://")) {
                dummySourceFilePath = "file://" + dummySourceFilePath.substring("file:".length)
            }
        } else {
            dummySourceFilePath = "file://$dummySourceFilePath"
        }
        val cleanURI = URI(dummySourceFilePath)
        val dummyFile = File(cleanURI)
        val projectRoot: File = dummyFile.parentFile.parentFile.parentFile.parentFile.parentFile.parentFile

        dummyFile.delete()
        dummySourceFile.delete()

        return projectRoot.absolutePath
    }

    private fun ffff(): String {
        try {
            val temp = processingEnv.filer.createSourceFile("temp" + System.currentTimeMillis())
            //val writer: Writer = temp.openWriter()
            val sourcePath = temp.toUri().path
            //writer.close()
            temp.delete()

            return sourcePath
        } catch (e: IOException) {
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Unable to determine source file path!")
        }

        return ""
    }

    private fun fff(): String {
        try {
            val generationForPath = processingEnv.filer.createSourceFile("PathFor" + javaClass.simpleName)
            val writer: Writer = generationForPath.openWriter()
            val sourcePath = generationForPath.toUri().path
            writer.close()
            generationForPath.delete()
            return sourcePath
        } catch (e: IOException) {
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Unable to determine source file path!")
        }

        return ""
    }

    private fun ff() {
        val log = processingEnv.messager

        val filer = processingEnv.filer
        //FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "tmp", (Element[]) null);
        val resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "tmp", null)
        val projectPath = Paths.get(resource.toUri()).parent.parent.parent.parent.parent.parent
        resource.delete()
        //val sourcePath = projectPath.resolve("src")
        log.printMessage(Diagnostic.Kind.WARNING, "sourcePath $projectPath")
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