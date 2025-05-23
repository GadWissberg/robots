package com.gadarts.robots.utils

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector3
import com.gadarts.robots.components.ComponentsMapper

object ModelUtils {
    private val auxVector1 = Vector3()
    fun getPositionOfModel(entity: Entity): Vector3 {
        return getPositionOfModel(entity, auxVector1)
    }

    fun getPositionOfModel(entity: Entity, output: Vector3): Vector3 {
        return ComponentsMapper.modelInstance.get(entity).gameModelInstance.modelInstance.transform.getTranslation(
            output
        )
    }


}
