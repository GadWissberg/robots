package com.gadarts.robots.systems

import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Vector3
import com.gadarts.robots.assets.GameAssetManager
import com.gadarts.robots.components.GameModelInstance
import com.gadarts.robots.definitions.ModelDefinition
import com.gadarts.robots.ecs.EntityBuilder
import com.gadarts.robots.systems.data.GameSessionData
import com.gadarts.robots.systems.data.GameSessionData.Companion.MAP_SIZE
import com.gadarts.robots.utils.ModelUtils

class MapSystem(
    messageDispatcher: MessageDispatcher,
    private val entityBuilder: EntityBuilder,
    private val assetsManager: GameAssetManager
) :
    GameEntitySystem(messageDispatcher) {
    private var mapGrid: Array<Array<ModelInstance?>> = Array(MAP_SIZE) { arrayOfNulls(MAP_SIZE) }
    private var blockModel: Model = ModelUtils.createBlockModel(Color.WHITE)

    override fun initialize(gameSessionData: GameSessionData) {
        super.initialize(gameSessionData)
        addBlock(0, 0)
        addBlock(0, 1)
        addBlock(0, 2)
        addBlock(1, 0)
        addPlayerCharacter(auxVector.set(0.5F, 0F, 0.5F))
        addPlayerCharacter(auxVector.set(0.5F, 0F, 1.5F))
    }

    private fun addPlayerCharacter(position: Vector3?) {
        entityBuilder.begin().addModelInstanceComponent(
            GameModelInstance(ModelInstance(assetsManager.getAssetByDefinition(ModelDefinition.COW)), null),
            position
        ).addCharacterComponent().finishAndAddToEngine()
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

    override fun dispose() {
        blockModel.dispose()
    }

    companion object {
        private val auxVector = Vector3()
    }
}
