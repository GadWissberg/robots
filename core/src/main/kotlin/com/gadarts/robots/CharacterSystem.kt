package com.gadarts.robots

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.math.Vector3
import com.gadarts.robots.components.ComponentsMapper
import com.gadarts.robots.ecs.CharacterComponent
import com.gadarts.robots.systems.GameEntitySystem
import com.gadarts.robots.systems.events.HandlerOnEvent
import com.gadarts.robots.systems.events.SystemEvents
import com.gadarts.robots.utils.ModelUtils

class CharacterSystem(messageDispatcher: MessageDispatcher) : GameEntitySystem(messageDispatcher) {
    private var selectedCharacter: Entity? = null
    private val characters by lazy { engine.getEntitiesFor(Family.all(CharacterComponent::class.java).get()) }

    override val subscribedEvents: Map<SystemEvents, HandlerOnEvent> = mapOf(
        SystemEvents.POSITION_CLICKED to HandlerOnEvent { msg, gameSessionData ->
            val characterAtPosition = characters.find {
                val positionOfModel = ModelUtils.getPositionOfModel(it)
                auxVector.set(positionOfModel.x.toInt().toFloat(), 0F, positionOfModel.z.toInt().toFloat())
                    .epsilonEquals(msg.extraInfo as Vector3)
            }
            deselect()
            if (characterAtPosition != null) {
                selectedCharacter = characterAtPosition
                setSelectedCharacterColor(Color.GREEN)
            }
        }
    )

    private fun deselect() {
        if (selectedCharacter != null) {
            setSelectedCharacterColor(Color.WHITE)
            selectedCharacter = null
        }
    }

    private fun setSelectedCharacterColor(color: Color) {
        val material =
            ComponentsMapper.modelInstance.get(selectedCharacter).gameModelInstance.modelInstance.materials[0]
        val colorAttribute =
            material.get(
                ColorAttribute.Diffuse
            ) as ColorAttribute
        colorAttribute.color.set(color)
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
    }

    override fun dispose() {

    }

    companion object {
        private val auxVector = Vector3()
    }
}
