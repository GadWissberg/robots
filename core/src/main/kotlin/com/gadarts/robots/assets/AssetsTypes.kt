package com.gadarts.robots.assets

import com.gadarts.robots.definitions.AssetDefinition
import com.gadarts.robots.definitions.ModelDefinition

enum class AssetsTypes(
    val format: String,
    val assets: Array<out AssetDefinition<*>> = arrayOf(),
    val loadedUsingLoader: Boolean = true,
    val skipAutoLoad: Boolean = false
) {
    MODELS("g3dj", ModelDefinition.entries.toTypedArray()),

}
