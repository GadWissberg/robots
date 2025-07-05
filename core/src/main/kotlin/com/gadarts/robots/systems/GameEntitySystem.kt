package com.gadarts.robots.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import com.badlogic.gdx.utils.Disposable
import com.gadarts.robots.systems.data.GameSessionData
import com.gadarts.robots.systems.events.HandlerOnEvent
import com.gadarts.robots.systems.events.SystemEvents

abstract class GameEntitySystem(private val messageDispatcher: MessageDispatcher) : Disposable, EntitySystem(),
    Telegraph {
    lateinit var gameSessionData: GameSessionData
    protected open val subscribedEvents: Map<SystemEvents, HandlerOnEvent> = emptyMap()


    override fun handleMessage(msg: Telegram?): Boolean {
        if (msg == null) return false

        val handlerOnEvent = subscribedEvents[SystemEvents.entries[msg.message]]
        handlerOnEvent?.react(
            msg,
            gameSessionData,
        )
        return false
    }

    open fun addListener() {
        subscribedEvents.forEach { messageDispatcher.addListener(this, it.key.ordinal) }
    }

    open fun initialize(
        gameSessionData: GameSessionData,
    ) {
        this.gameSessionData = gameSessionData
    }

    open fun onSystemReady() {}

}
