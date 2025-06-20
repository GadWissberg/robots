package com.gadarts.robots.components

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.collision.BoundingBox
import com.gadarts.robots.definitions.ModelDefinition

class GameModelInstance(
    val modelInstance: ModelInstance,
    private val definition: ModelDefinition?,
) {
    var sphere: Boolean = false
        private set


    private val boundingBox = BoundingBox()

    fun getBoundingBox(auxBox: BoundingBox): BoundingBox {
        return auxBox.set(boundingBox)
    }

    override fun toString(): String {
        return definition.toString()
    }


}
