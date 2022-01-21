package com.rtchubs.arfixture.sceneform

import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.sceneform.ArTree
import java.util.ArrayList

fun getArTreeData(): ArrayList<ArTree> {
    val arTreeList = ArrayList<ArTree>()

    arTreeList.add(ArTree("Tree 1", R.drawable.ar_preview_tree_one, R.raw.model))
    arTreeList.add(ArTree("Tree 2", R.drawable.ar_preview_tree_two, R.raw.tree_two))
    arTreeList.add(ArTree("Tree 3", R.drawable.ar_preview_tree_three, R.raw.tree_three))
    arTreeList.add(ArTree("Tree 4", R.drawable.ar_preview_tree_four, R.raw.tree_four))
    arTreeList.add(ArTree("Tree 5", R.drawable.ar_preview_tree_five, R.raw.tree_five))
    arTreeList.add(ArTree("Tree 6", R.drawable.ar_preview_tree_six, R.raw.tree_six))
    arTreeList.add(ArTree("Tree 7", R.drawable.ar_preview_tree_seven, R.raw.tree_seven))
    arTreeList.add(ArTree("Tree 8", R.drawable.ar_preview_tree_eight, R.raw.tree_eight))
    arTreeList.add(ArTree("Tree 9", R.drawable.ar_preview_tree_nine, R.raw.tree_nine))

    return arTreeList
}