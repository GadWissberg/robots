package com.gadarts.robots.systems

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.gadarts.robots.GameDebugSettings
import com.gadarts.robots.systems.data.GameSessionData

class CameraSystem : GameEntitySystem(), InputProcessor {
    private val debugInput: CameraInputController by lazy { CameraInputController(gameSessionData.renderData.camera) }
    private val cameraPanVelocity = Vector2()
    private var mouseX = 0
    private var mouseY = 0
    override fun initialize(gameSessionData: GameSessionData) {
        super.initialize(gameSessionData)
        if (GameDebugSettings.CAMERA_DEBUG_INPUT) {
            debugInput.autoUpdate = true
            Gdx.input.inputProcessor = debugInput
        } else {
            (Gdx.input.inputProcessor as InputMultiplexer).addProcessor(this)
        }
    }

    override fun onSystemReady() {
        super.onSystemReady()
        positionCamera()
    }

    override fun update(deltaTime: Float) {
        panCameraFromScreenEdges()
        gameSessionData.renderData.camera.update()
        if (GameDebugSettings.CAMERA_DEBUG_INPUT) {
            debugInput.update()
        }
    }


    override fun dispose() {
    }


    private fun positionCamera(
    ) {
        val renderData = gameSessionData.renderData
        val camera = renderData.camera
        camera.position.set(
            auxVector3_1.set(16F, 16F, 16F)
        )
        camera.lookAt(Vector3.Zero)
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
        return false
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
        mouseX = screenX
        mouseY = screenY
        return true
    }

    private fun smoothDamp(current: Float, target: Float): Float {
        val delta = Gdx.graphics.deltaTime
        val omega = 2f / 0.3F
        val x = omega * delta
        val exp = 1f / (1f + x + 0.48f * x * x + 0.235f * x * x * x)
        return (current - target) * exp + target
    }

    private fun panCameraFromScreenEdges() {
        calculateCameraPanVelocity()
        val camera = gameSessionData.renderData.camera
        val right = auxVector3_1.set(camera.direction).crs(camera.up).nor()
        val forward = auxVector3_2.set(camera.direction)
            .set(camera.direction.x, 0f, camera.direction.z)
            .nor()
        camera.position.add(
            right.x * cameraPanVelocity.x * Gdx.graphics.deltaTime,
            0f,
            right.z * cameraPanVelocity.x * Gdx.graphics.deltaTime
        )
        camera.position.add(
            forward.x * cameraPanVelocity.y * Gdx.graphics.deltaTime,
            0f,
            forward.z * cameraPanVelocity.y * Gdx.graphics.deltaTime
        )
        camera.position.x = MathUtils.clamp(camera.position.x, CAMERA_MIN_POSITION, CAMERA_MAX_POSITION)
        camera.position.z = MathUtils.clamp(camera.position.z, CAMERA_MIN_POSITION, CAMERA_MAX_POSITION)
    }

    private fun calculateCameraPanVelocity() {
        var targetX = 0f
        var targetY = 0f
        if (mouseX <= CAMERA_EDGE_MARGIN) targetX = -CAMERA_MAX_SPEED
        else if (mouseX >= Gdx.graphics.width - CAMERA_EDGE_MARGIN) targetX = CAMERA_MAX_SPEED
        if (mouseY <= CAMERA_EDGE_MARGIN) targetY = CAMERA_MAX_SPEED
        else if (mouseY >= Gdx.graphics.height - CAMERA_EDGE_MARGIN) targetY = -CAMERA_MAX_SPEED
        cameraPanVelocity.x = smoothDamp(cameraPanVelocity.x, targetX)
        cameraPanVelocity.y = smoothDamp(cameraPanVelocity.y, targetY)
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }

    companion object {
        private val auxVector3_1 = Vector3()
        private val auxVector3_2 = Vector3()
        private const val CAMERA_EDGE_MARGIN = 40
        private const val CAMERA_MAX_SPEED = 10F
        private const val CAMERA_MIN_POSITION = 12F
        private const val CAMERA_MAX_POSITION = 40F
    }
}
