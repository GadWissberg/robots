package com.gadarts.robots.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.gadarts.robots.components.GameModelInstance
import com.gadarts.robots.components.ModelInstanceComponent

class EntityBuilder(private val engine: PooledEngine) {
    private var currentEntity: Entity? = null

    fun addModelInstanceComponent(modelInstance: GameModelInstance) {
        currentEntity!!.add(ModelInstanceComponent(modelInstance))
    }

    fun begin() {
        this.currentEntity = Entity()
    }

    fun finishAndAddToEngine() {
        engine.addEntity(currentEntity)
        currentEntity = null
    }
}
