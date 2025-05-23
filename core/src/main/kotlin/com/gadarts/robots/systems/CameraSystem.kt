package com.gadarts.robots.systems

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.math.Vector3
import com.gadarts.robots.GameDebugSettings
import com.gadarts.robots.systems.data.GameSessionData

class CameraSystem : GameEntitySystem() {
    private val debugInput: CameraInputController by lazy { CameraInputController(gameSessionData.renderData.camera) }

    override fun initialize(gameSessionData: GameSessionData) {
        super.initialize(gameSessionData)
        if (GameDebugSettings.DEBUG_INPUT) {
            debugInput.autoUpdate = true
            Gdx.input.inputProcessor = debugInput
        }
    }

    override fun onSystemReady() {
        super.onSystemReady()
        positionCamera()
    }

    override fun update(deltaTime: Float) {
        gameSessionData.renderData.camera.update()
        if (GameDebugSettings.DEBUG_INPUT) {
            debugInput.update()
        }
    }


    override fun dispose() {
    }


    private fun positionCamera(
    ) {
        val renderData = gameSessionData.renderData
        renderData.camera.position.set(
            auxVector3_1.set(0F, 1F, 1F)
        )
    }


    companion object {
        private val auxVector3_1 = Vector3()
    }


}
