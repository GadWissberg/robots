package com.gadarts.robots.systems

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3
import com.gadarts.robots.components.GameModelInstance
import com.gadarts.robots.ecs.EntityBuilder
import com.gadarts.robots.systems.data.GameSessionData

class MapSystem(private val entityBuilder: EntityBuilder) : GameEntitySystem() {
    private var mapGrid: Array<Array<ModelInstance?>>
    private var blockModel: Model

    init {
        blockModel = createBlockModel()
        mapGrid = Array(MAPS_SIZE) { arrayOfNulls(MAPS_SIZE) }
    }

    override fun initialize(gameSessionData: GameSessionData) {
        super.initialize(gameSessionData)
        addBlock(0, 0)
        addBlock(0, 1)
        addBlock(0, 2)
        addBlock(1, 0)
    }

    private fun addBlock(z: Int, x: Int) {
        mapGrid[z][x] = ModelInstance(blockModel).apply {
            transform.setTranslation(Vector3(x.toFloat(), 0f, z.toFloat()))
        }
        entityBuilder.begin()
        entityBuilder.addModelInstanceComponent(
            GameModelInstance(mapGrid[z][x]!!, null),
        )
        entityBuilder.finishAndAddToEngine()
    }

    private fun createBlockModel(): Model {
        val modelBuilder = ModelBuilder()
        modelBuilder.begin()
        val attributes = VertexAttributes.Usage.Position.toLong()
        val material = Material(ColorAttribute.createDiffuse(Color.WHITE))
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

    override fun dispose() {
        blockModel.dispose()
    }

    companion object {
        private const val MAPS_SIZE = 32
    }
}
