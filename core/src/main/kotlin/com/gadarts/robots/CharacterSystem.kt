package com.gadarts.robots

import com.badlogic.ashley.core.Family
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.math.Vector3
import com.gadarts.robots.ecs.CharacterComponent
import com.gadarts.robots.systems.GameEntitySystem
import com.gadarts.robots.systems.events.HandlerOnEvent
import com.gadarts.robots.systems.events.SystemEvents
import com.gadarts.robots.utils.ModelUtils

class CharacterSystem(messageDispatcher: MessageDispatcher) : GameEntitySystem(messageDispatcher) {
    private val characters by lazy { engine.getEntitiesFor(Family.all(CharacterComponent::class.java).get()) }

    override val subscribedEvents: Map<SystemEvents, HandlerOnEvent> = mapOf(
        SystemEvents.POSITION_CLICKED to HandlerOnEvent { msg, gameSessionData ->
            characters.find {
                val positionOfModel = ModelUtils.getPositionOfModel(it)
                auxVector.set(positionOfModel.x.toInt().toFloat(), 0F, positionOfModel.z.toInt().toFloat())
                    .epsilonEquals(msg.extraInfo as Vector3)
            }
                ?.let { character ->
                    Gdx.app.log(
                        "CharacterSystem",
                        "Character clicked at position: ${msg.extraInfo} with entity: $character"
                    )
                }
        }
    )

    override fun dispose() {

    }

    companion object {
        private val auxVector = Vector3()
    }
}
