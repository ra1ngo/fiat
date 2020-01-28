package ru.raingo.codegen

import com.google.auto.service.AutoService
import ru.raingo.annotation.Template
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

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


    override fun process(set: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {

        return false
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

}