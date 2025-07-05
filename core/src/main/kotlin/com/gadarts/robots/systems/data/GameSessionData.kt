package com.gadarts.robots.systems.data

import com.badlogic.gdx.utils.Disposable

class GameSessionData(
) :
    Disposable {
    val renderData = GameSessionDataRender()


    override fun dispose() {
        renderData.dispose()
    }

    companion object {
        const val MAP_SIZE = 32
    }
}
