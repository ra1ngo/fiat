package ru.raingo.fiat.yar

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

class ViewBuilder(val context: Context) {
    val TAG = "YarViewBuilder"
    private lateinit var root: ViewGroup
    private val stack: MutableList<View> = mutableListOf()

    fun build(ast: Node): ViewGroup? {
        processingAst(ast)

        return root
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
        val action = actions[node.tag] ?: return View(context)
        return action(node)
    }

    //ACTIONS
    val actions = mapOf<Tag, (Node)->View>(
        Tag.ROOT to ::onRoot,
        Tag.LAY to ::onLay,
        Tag.TEXT to ::onText
    )

    fun onRoot(node: Node): ViewGroup {
        Log.d(TAG, "onStart")
        root = FrameLayout(context)

        return root
    }

    fun onLay(node: Node): ViewGroup {
        Log.d(TAG, "onLay")
        val view = LinearLayout(context)

        val parent = stack[stack.size - 1] as ViewGroup
        parent.addView(view)

        return view
    }

    fun onText(node: Node): TextView {
        Log.d(TAG, "onText")
        val view = TextView(context)
        view.text = node.textList.joinToString("","","")
        view.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        val parent = stack[stack.size - 1] as ViewGroup
        parent.addView(view)

        return view
    }
}