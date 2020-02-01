package ru.raingo.codegen

import com.google.auto.service.AutoService
import ru.raingo.annotation.Template
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.ElementKindVisitor8
import javax.lang.model.util.SimpleElementVisitor8
import javax.tools.Diagnostic
import javax.tools.StandardLocation

@AutoService(Processor::class) // For registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(AstGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class AstGenerator : AbstractProcessor(){

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





                log.printMessage(Diagnostic.Kind.WARNING, "rootElements $rootElements")
                for (s in rootElements) {
                    log.printMessage(Diagnostic.Kind.WARNING, "rootElement $s")
                }

                log.printMessage(Diagnostic.Kind.WARNING, "annotatedElements $annotatedElements")
                for (q in annotatedElements) {
                    log.printMessage(Diagnostic.Kind.WARNING, "annotatedElement $q")
                }


                log.printMessage(Diagnostic.Kind.WARNING, "getAllAnnotationMirrors ${el.getAllAnnotationMirrors(it)}")
                for (i in el.getAllAnnotationMirrors(it)) {
                    log.printMessage(Diagnostic.Kind.WARNING, "mirror: ${i.annotationType} ${i.elementValues}")
                    for (e in i.elementValues) {
                        log.printMessage(Diagnostic.Kind.WARNING, "elementValues: ${e.key} ${e.value}")
                    }
                }
                log.printMessage(Diagnostic.Kind.WARNING, "getDocComment ${el.getDocComment(it)}")
                log.printMessage(Diagnostic.Kind.WARNING, "getPackageOf ${el.getPackageOf(it)}")
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










                    if ("$p" == "str") {
                        p.accept(object : ElementKindVisitor8<Void, Void>() {
                            override fun defaultAction(p0: Element?, p1: Void?): Void? {
                                log.printMessage(Diagnostic.Kind.WARNING, "defaultAction $p0")
                                return super.defaultAction(p0, p1)
                            }

                            override fun visitVariable(field: VariableElement?, p1: Void?): Void? {
                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.enclosingElement}")
                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.constantValue}")
                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.annotationMirrors}")
                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.annotationMirrors?.first()?.elementValues}")
                                log.printMessage(Diagnostic.Kind.WARNING, "visitVariable ${field?.annotationMirrors?.first()?.annotationType}")

                                return super.visitVariable(field, p1)
                            }
                        }, null)
                    }













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

                ///////////////

                val fieldName = it.simpleName.toString()
                val pack = processingEnv.elementUtils.getPackageOf(it).toString()
                generateAst(fieldName, pack)
            }
        return true
    }

    private fun generateAst(className: String, pack: String){
        val fileName = "Yard_$className"
        val fileContent = AstBuilder(fileName,pack).getContent()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val file = File(kaptKotlinGeneratedDir, "$fileName.kt")

        file.writeText(fileContent)
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}