package ru.raingo.fiat.yar

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class ViewBuilder(val context: Context) {
    val TAG = "YarViewBuilder"
    private var view: ViewGroup? = null
    private var currentNode: Node? = null

    fun build(ast: Node): ViewGroup? {
        processingAst(ast)

        return view
    }

    private fun processingAst(parent: Node) {
        processingNode(parent)
        for (node in parent.nodes) {
            processingAst(node)
        }
    }

    private fun processingNode(node: Node) {
        val action = actions[node.tag] ?: return
        currentNode = node
        action()
    }

    //ACTIONS
    val actions = mapOf<Tag, ()->Unit>(
        Tag.ROOT to ::onRoot,
        Tag.LAY to ::onLay,
        Tag.TEXT to ::onText
    )

    fun onRoot() {
        Log.d(TAG, "onStart")
    }

    fun onLay() {
        Log.d(TAG, "onLay")
        view = LinearLayout(context)
    }

    fun onText() {
        Log.d(TAG, "onText")
        val childView = TextView(context)
        childView.text = currentNode?.textList?.joinToString("","","")
        childView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        view?.addView(childView)
    }
}