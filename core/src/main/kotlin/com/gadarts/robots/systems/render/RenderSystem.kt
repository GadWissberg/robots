package com.gadarts.robots.systems.render

import com.badlogic.ashley.core.Family
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider
import com.gadarts.robots.GeneralUtils
import com.gadarts.robots.components.ModelInstanceComponent
import com.gadarts.robots.systems.GameEntitySystem
import com.gadarts.robots.systems.data.GameSessionData

class RenderSystem : GameEntitySystem() {
    private val relatedEntities: RenderSystemRelatedEntities by lazy {
        RenderSystemRelatedEntities(
            engine!!.getEntitiesFor(
                Family.all(ModelInstanceComponent::class.java)
                    .get()
            ),
        )
    }

    private val batches: RenderSystemBatches by lazy {
        RenderSystemBatches(
            DecalBatch(
                DECALS_POOL_SIZE,
                CameraGroupStrategy(gameSessionData.renderData.camera)
            ),
            ModelBatch(),
            ModelBatch(DepthShaderProvider())
        )
    }
    private val modelsRenderer by lazy {
        ModelsRenderer(
            relatedEntities,
            gameSessionData.renderData,
        )
    }


    override fun initialize(gameSessionData: GameSessionData) {
        super.initialize(gameSessionData)
        modelsRenderer.initializeDirectionalLightAndShadows()
    }

    override fun update(deltaTime: Float) {
        GeneralUtils.clearScreen()
        modelsRenderer.renderModels(
            batch = batches.modelBatch,
            camera = gameSessionData.renderData.camera,
            applyEnvironment = true,
            forShadow = false
        )
    }

    override fun dispose() {
        batches.dispose()
        modelsRenderer.dispose()
    }


    companion object {
        const val DECALS_POOL_SIZE = 200
    }

}
