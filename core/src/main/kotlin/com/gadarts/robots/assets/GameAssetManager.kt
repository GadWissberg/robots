package com.gadarts.robots.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.MathUtils
import com.gadarts.robots.definitions.AssetDefinition

open class GameAssetManager : AssetManager() {

    fun loadAssets() {
        loadAllAssets()
        finishLoading()
    }

    inline fun <reified T> getAssetByDefinition(definition: AssetDefinition<T>, index: Int = -1): T {
        val paths = definition.getPaths()
        return get(paths[if (index == -1) MathUtils.random(paths.size - 1) else index], T::class.java)
    }

    private fun loadAllAssets() {
        AssetsTypes.entries.forEach { type ->
            if (!type.skipAutoLoad) {
                if (type.loadedUsingLoader) {
                    if (type.assets.isNotEmpty()) {
                        type.assets.forEach { asset ->
                            asset.getPaths().forEach {
                                load(it, asset.getClazz())
                            }
                        }
                    }
                } else {
                    loadTextualAsset(type)
                }
            }
        }
    }

    private fun loadTextualAsset(type: AssetsTypes) {
        type.assets.forEach { asset ->
            asset.getPaths().forEach {
                val content = Gdx.files.internal(it).readString()
                addAsset(
                    it,
                    String::class.java,
                    content
                )
            }
        }
    }


}
