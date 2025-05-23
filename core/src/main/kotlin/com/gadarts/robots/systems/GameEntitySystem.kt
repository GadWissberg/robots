package com.gadarts.robots.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import com.badlogic.gdx.utils.Disposable
import com.gadarts.robots.systems.data.GameSessionData

abstract class GameEntitySystem : Disposable, EntitySystem(),
    Telegraph {
    lateinit var gameSessionData: GameSessionData


    override fun handleMessage(msg: Telegram?): Boolean {
        return false
    }

    open fun initialize(
        gameSessionData: GameSessionData,
    ) {
        this.gameSessionData = gameSessionData
    }

    open fun onSystemReady() {}

}
