package com.gadarts.robots.utils

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
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

    fun createBlockModel(color: Color?): Model {
        val modelBuilder = ModelBuilder()
        modelBuilder.begin()
        val attributes = VertexAttributes.Usage.Position.toLong()
        val material = Material(ColorAttribute.createDiffuse(color))
        val partBuilder = modelBuilder.part("box", GL20.GL_LINES, attributes, material)
        val size = 1f
        val v000 = Vector3(0f, 0f, 0f)
        val v001 = Vector3(0f, 0f, size)
        val v010 = Vector3(0f, size, 0f)
        val v011 = Vector3(0f, size, size)
        val v100 = Vector3(size, 0f, 0f)
        val v101 = Vector3(size, 0f, size)
        val v110 = Vector3(size, size, 0f)
        val v111 = Vector3(size, size, size)
        val edges = arrayOf(
            Pair(v000, v001), Pair(v000, v010), Pair(v000, v100),
            Pair(v001, v011), Pair(v001, v101), Pair(v010, v011),
            Pair(v010, v110), Pair(v011, v111), Pair(v100, v101),
            Pair(v100, v110), Pair(v101, v111), Pair(v110, v111)
        )
        for ((start, end) in edges) {
            partBuilder.line(start, end)
        }
        return modelBuilder.end()
    }

}
