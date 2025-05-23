package com.gadarts.robots.definitions

import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.graphics.g3d.Model

enum class ModelDefinition(
    fileNames: Int = 1,
) :
    AssetDefinition<Model> {

    ;

    private val pathFormat = "models/%s.g3dj"
    private val paths = ArrayList<String>()

    init {
        initializePaths(pathFormat, getPaths(), fileNames)
    }

    override fun getPaths(): ArrayList<String> {
        return paths
    }

    override fun getParameters(): AssetLoaderParameters<Model>? {
        return null
    }

    override fun getClazz(): Class<Model> {
        return Model::class.java
    }

    override fun getDefinitionName(): String {
        return name
    }

}
