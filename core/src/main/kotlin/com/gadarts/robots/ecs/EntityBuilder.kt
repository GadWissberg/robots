package com.gadarts.robots.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector3
import com.gadarts.robots.components.GameModelInstance
import com.gadarts.robots.components.ModelInstanceComponent

class EntityBuilder(private val engine: PooledEngine) {
    private var currentEntity: Entity? = null

    fun addModelInstanceComponent(modelInstance: GameModelInstance, position: Vector3? = null): EntityBuilder {
        currentEntity!!.add(ModelInstanceComponent(modelInstance))
        if (position != null) {
            modelInstance.modelInstance.transform.setTranslation(position)
        }
        return this
    }

    fun begin(): EntityBuilder {
        val entity = Entity()
        this.currentEntity = entity
        return this
    }

    fun finishAndAddToEngine() {
        engine.addEntity(currentEntity)
        currentEntity = null
    }
}
