package com.gadarts.robots.systems.events

import com.badlogic.gdx.ai.msg.Telegram
import com.gadarts.robots.systems.data.GameSessionData

fun interface HandlerOnEvent {
    fun react(
        msg: Telegram,
        gameSessionData: GameSessionData,
    )

}
