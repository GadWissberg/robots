package com.gadarts.robots

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.gadarts.robots.assets.GameAssetManager

class Robots : Game() {
    private val assetsManager: GameAssetManager by lazy { GameAssetManager() }

    override fun create() {
        assetsManager.loadAssets()
        val screenWidth = Gdx.graphics.displayMode.width
        val screenHeight = Gdx.graphics.displayMode.height
        val targetWidth = (screenWidth * 0.85).toInt().coerceAtMost(MAX_RESOLUTION_WIDTH)
        val targetHeight = (screenHeight * 0.85).toInt().coerceAtMost(MAX_RESOLUTION_HEIGHT)
        Gdx.graphics.setWindowedMode(targetWidth, targetHeight)
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
        Gdx.input.inputProcessor = InputMultiplexer()
        setScreen(
            GamePlayScreen(assetsManager
            )
        )
    }

    companion object {
        const val MAX_RESOLUTION_WIDTH = 1920
        const val MAX_RESOLUTION_HEIGHT = 1080
    }
}
