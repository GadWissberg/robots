package com.gadarts.robots

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Screen
import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.gadarts.robots.assets.GameAssetManager
import com.gadarts.robots.ecs.EcsManager
import com.gadarts.robots.ecs.EntityBuilder
import com.gadarts.robots.systems.CameraSystem
import com.gadarts.robots.systems.GameEntitySystem
import com.gadarts.robots.systems.MapSystem
import com.gadarts.robots.systems.data.GameSessionData
import com.gadarts.robots.systems.render.RenderSystem

class GamePlayScreen(private val assetsManager: GameAssetManager) :
    Screen {
    private val engine: PooledEngine by lazy { PooledEngine() }
    private val gameSessionData: GameSessionData by lazy {
        GameSessionData(
        )
    }

    override fun dispose() {

    }

    override fun show() {
        val entityBuilder = EntityBuilder(engine)
        val messageDispatcher = MessageDispatcher()
        EcsManager(engine, entityBuilder)
        val systems = listOf(
            RenderSystem(messageDispatcher),
            CameraSystem(messageDispatcher),
            MapSystem(messageDispatcher, entityBuilder, assetsManager),
            CharacterSystem(messageDispatcher),
            InterfaceSystem(messageDispatcher, entityBuilder)
        )
        systems.forEach {
            engine.addSystem(it)
        }
        systems.forEach { system ->
            system.addListener()
        }
        engine.systems.forEach {
            (it as GameEntitySystem).initialize(
                gameSessionData
            )
        }
        engine.systems.forEach {
            (it as GameEntitySystem).onSystemReady()
        }
    }


    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

}
