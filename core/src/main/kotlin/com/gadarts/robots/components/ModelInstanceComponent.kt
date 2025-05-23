package com.gadarts.robots.components

import com.badlogic.ashley.core.Component

class ModelInstanceComponent : Component {
    var hidden: Boolean = false
    var hideAt: Long = -1
    lateinit var gameModelInstance: GameModelInstance


}
