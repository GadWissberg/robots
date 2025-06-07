package com.gadarts.robots.components

import com.badlogic.ashley.core.Component

class ModelInstanceComponent(val gameModelInstance: GameModelInstance) : Component {
    var hidden: Boolean = false


}
