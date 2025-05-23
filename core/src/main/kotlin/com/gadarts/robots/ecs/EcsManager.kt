package com.gadarts.robots.ecs

import com.badlogic.ashley.core.PooledEngine

class EcsManager(
    val engine: PooledEngine,
    val entityBuilder: EntityBuilder,
)
