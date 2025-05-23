package com.gadarts.robots.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.TimeUtils
import com.gadarts.robots.components.ComponentsMapper
import com.gadarts.robots.components.GameModelInstance
import com.gadarts.robots.systems.data.GameSessionDataRender

class ModelsRenderer(
    private val relatedEntities: RenderSystemRelatedEntities,
    private val renderData: GameSessionDataRender,
) : Disposable {
    private var axisModelHandler = AxisModelHandler()
    private val shadowLight: DirectionalShadowLight by lazy {
        DirectionalShadowLight(
            SHADOW_MAP_SIZE,
            SHADOW_MAP_SIZE,
            SHADOW_VIEWPORT_SIZE,
            SHADOW_VIEWPORT_SIZE,
            .1f,
            150f
        )
    }

    fun renderModels(
        batch: ModelBatch,
        camera: Camera,
        applyEnvironment: Boolean,
        forShadow: Boolean
    ) {
        batch.begin(camera)
        axisModelHandler.render(batch)
        for (entity in relatedEntities.modelInstanceEntities) {
            renderModel(entity, batch, applyEnvironment, forShadow)
        }
        batch.end()
    }

    private fun renderModel(entity: Entity, batch: ModelBatch, applyEnvironment: Boolean, forShadow: Boolean = false) {
        if (isVisible(entity, forShadow)) {
            val modelInstanceComponent = ComponentsMapper.modelInstance.get(entity)
            renderGameModelInstance(modelInstanceComponent.gameModelInstance, applyEnvironment, batch)
            if (!modelInstanceComponent.hidden
                && modelInstanceComponent.hideAt != -1L
                && modelInstanceComponent.hideAt <= TimeUtils.millis()
            ) {
                modelInstanceComponent.hidden = true
                modelInstanceComponent.hideAt = -1L
            }
        }
    }

    private fun renderGameModelInstance(
        gameModelInstance: GameModelInstance,
        applyEnvironment: Boolean,
        batch: ModelBatch
    ) {
        val modelInstance = gameModelInstance.modelInstance
        if (applyEnvironment) {
            batch.render(modelInstance, environment)
        } else {
            batch.render(modelInstance)
        }
    }

    private fun isVisible(entity: Entity, extendBoundingBoxSize: Boolean): Boolean {
        val modelInsComp = ComponentsMapper.modelInstance[entity]
        val gameModelInstance = modelInsComp.gameModelInstance
        val boundingBox = gameModelInstance.getBoundingBox(auxBox)
        val dims: Vector3 = boundingBox.getDimensions(auxVector2)

        if (modelInsComp.hidden) return false
        if (dims.isZero) return false

        val center: Vector3 =
            gameModelInstance.modelInstance.transform.getTranslation(auxVector1)
        dims.scl(if (extendBoundingBoxSize) 16.6F else 1.3F)

        val frustum = renderData.camera.frustum
        val isInFrustum = if (gameModelInstance.sphere) frustum.sphereInFrustum(
            gameModelInstance.modelInstance.transform.getTranslation(
                auxVector1
            ), dims.len2()
        )
        else frustum.boundsInFrustum(center, dims)

        return isInFrustum
    }

    private val environment: Environment by lazy { Environment() }

    fun initializeDirectionalLightAndShadows() {
        extracted()
        val dirValue = 0.4f
        shadowLight.set(dirValue, dirValue, dirValue, 0.3F, -0.7f, -0.3f)
    }

    private fun extracted() {
        environment.set(
            ColorAttribute(
                ColorAttribute.AmbientLight,
                Color(0.9F, 0.9F, 0.9F, 1F)
            )
        )
    }

    override fun dispose() {
        shadowLight.dispose()
    }

    companion object {
        private const val SHADOW_VIEWPORT_SIZE: Float = 38F
        private const val SHADOW_MAP_SIZE = 2056
        private val auxVector1 = Vector3()
        private val auxVector2 = Vector3()
        private val auxBox = com.badlogic.gdx.math.collision.BoundingBox()
    }
}
