package ru.raingo.codegen.yar

import com.squareup.kotlinpoet.*
import javax.annotation.processing.Messager

//import android.content.Context
//import android.view.View
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import android.widget.LinearLayout
//import android.widget.TextView

val widgetPackage = "android.widget"
val viewPackage = "android.view"

const val ROOT = "root"
const val VIEW_NAME = "View"
const val VIEW_GROUP_NAME = "ViewGroup"
const val TEXT_VIEW_NAME = "TextView"
const val FRAME_LAYOUT_NAME = "FrameLayout"
const val LINEAR_LAYOUT_NAME = "LinearLayout"

enum class ViewClass(val className: ClassName){
    CONTEXT(ClassName("android.content", "Context")),
    VIEW(ClassName(viewPackage, VIEW_NAME)),
    VIEW_GROUP(ClassName(viewPackage, VIEW_GROUP_NAME)),
    TEXT_VIEW(ClassName(widgetPackage, TEXT_VIEW_NAME)),
    FRAME_LAYOUT(ClassName(widgetPackage, FRAME_LAYOUT_NAME)),
    LINEAR_LAYOUT(ClassName(widgetPackage, LINEAR_LAYOUT_NAME))
}

typealias View = Pair<String, ViewClass>

class ViewBuilder(private val log: Messager) {
    //private lateinit var root: View
    private val views: MutableList<View> = mutableListOf()
    private val stack: MutableList<View> = mutableListOf()
    private var countView = 0

    private val bindSpec = FunSpec.builder("bind")
    private val buildSpec = FunSpec.builder("build")

    fun build(
        root: Node,
        clazz: TypeSpec.Builder,
        className: String,
        packageName: String
    ) {
        processingAst(root)

        bindSpec.addParameter("context", ViewClass.CONTEXT.className)
        formBindSpec(clazz)
        bindSpec.returns(ClassName(packageName, className)).addStatement("")
        bindSpec.addStatement("return this")

        buildSpec.returns(ViewClass.VIEW.className).addStatement("return root")

        clazz.addFunction(bindSpec.build())
        clazz.addFunction(buildSpec.build())
    }

    private fun formBindSpec(clazz: TypeSpec.Builder) {
        for (view in views) {
            val property = PropertySpec.builder(view.first, view.second.className)
                .mutable()
                .addModifiers(KModifier.PRIVATE, KModifier.LATEINIT)
                .build()
            clazz.addProperty(property)

            bindSpec.addStatement("${view.first} = ${view.second.className.simpleName}(context)")
        }
    }


    private fun processingAst(node: Node) {
        val view = processingNode(node)
        stack.add(view)
        for (child in node.nodes) {
            processingAst(child)
        }
        stack.removeAt(stack.size - 1)
    }

    private fun processingNode(node: Node): View {
        val action = actions[node.tag] ?: return onDefault(node)
        return action(node)
    }

    //ACTIONS
    //сайд-эффект, каждую функцию разделить на
    //добавление в views для bind
    //создание функции build
    //добавление в соответствующую функцию refresh, которые отвечают за перерисовки при изменении пропсов
    val actions = mapOf<Tag, (Node)->View>(
        Tag.ROOT to ::onRoot,
        Tag.LAY to ::onLay,
        Tag.TEXT to ::onText
    )

    fun onRoot(node: Node): View {
        val view = View(ROOT.decapitalize(), ViewClass.FRAME_LAYOUT)
        views.add(view)

        return view
    }

    fun onDefault(node: Node): View {
        val view = View("${VIEW_NAME.decapitalize()}${++countView}", ViewClass.VIEW)
        views.add(view)

        return view
    }

    fun onLay(node: Node): View {
        val view = View("${LINEAR_LAYOUT_NAME.decapitalize()}${++countView}", ViewClass.LINEAR_LAYOUT)
        views.add(view)

        val parent = stack[stack.size - 1]

        buildSpec.addStatement("${parent.first}.addView(${view.first})\n")

        return view
    }

    fun onText(node: Node): View {
        val view = View("${TEXT_VIEW_NAME.decapitalize()}${++countView}", ViewClass.TEXT_VIEW)
        views.add(view)

        val parent = stack[stack.size - 1]

        buildSpec.addCode("""
            ${view.first}.text = "${node.textList.joinToString("","","")}"
            ${view.first}.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            ${parent.first}.addView(${view.first})
        """.trimIndent() + "\n\n")

        return view
    }
}