package com.gadarts.robots

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.utils.ScreenUtils

object GeneralUtils {


    fun createCamera(fov: Float): PerspectiveCamera {
        val perspectiveCamera = PerspectiveCamera(
            fov,
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat()
        )
        perspectiveCamera.near = 0.1F
        perspectiveCamera.far = 300F
        return perspectiveCamera
    }

    fun clearScreen() {
        Gdx.gl.glViewport(
            0, 0,
            Gdx.graphics.backBufferWidth,
            Gdx.graphics.backBufferHeight
        )
        ScreenUtils.clear(Color.BLACK, true)
        Gdx.gl.glClearColor(0F, 0F, 0F, 1F)
        Gdx.gl.glClear(
            GL20.GL_COLOR_BUFFER_BIT
                or GL20.GL_DEPTH_BUFFER_BIT
                or if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0
        )
    }




}
