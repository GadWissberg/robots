package com.gadarts.robots

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Plane
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.Ray
import com.gadarts.robots.components.GameModelInstance
import com.gadarts.robots.ecs.EntityBuilder
import com.gadarts.robots.systems.GameEntitySystem
import com.gadarts.robots.systems.data.GameSessionData
import com.gadarts.robots.systems.events.SystemEvents
import com.gadarts.robots.utils.ModelUtils

class InterfaceSystem(private val messageDispatcher: MessageDispatcher, private val entityBuilder: EntityBuilder) :
    GameEntitySystem(messageDispatcher), InputProcessor {

    private val cursorModel = ModelUtils.createBlockModel(Color.YELLOW)
    private val cursorModelInstance: GameModelInstance = GameModelInstance(ModelInstance(cursorModel), null)
    override fun initialize(gameSessionData: GameSessionData) {
        super.initialize(gameSessionData)
        (Gdx.app.input.inputProcessor as InputMultiplexer).addProcessor(this)
        entityBuilder.begin()
        entityBuilder.addModelInstanceComponent(cursorModelInstance, null)
        entityBuilder.finishAndAddToEngine()
    }

    override fun dispose() {
        cursorModel.dispose()
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        messageDispatcher.dispatchMessage(
            SystemEvents.POSITION_CLICKED.ordinal,
            cursorModelInstance.modelInstance.transform.getTranslation(auxVector)
        )
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {

        val worldRay: Ray = gameSessionData.renderData.camera.getPickRay(screenX.toFloat(), screenY.toFloat())

        if (Intersector.intersectRayPlane(worldRay, plane, auxVector)) {
            val mapSize = GameSessionData.MAP_SIZE.toFloat()
            auxVector.x = MathUtils.clamp(auxVector.x.toInt().toFloat(), 0F, mapSize)
            auxVector.y = 0f
            auxVector.z = MathUtils.clamp(auxVector.z.toInt().toFloat(), 0F, mapSize)

            cursorModelInstance.modelInstance.transform.setToTranslation(auxVector)
        }

        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }

    companion object {
        private val auxVector = Vector3()
        private val plane = Plane(Vector3.Y, 0f)
    }
}
