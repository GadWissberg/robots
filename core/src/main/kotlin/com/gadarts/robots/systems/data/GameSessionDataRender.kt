package com.gadarts.robots.systems.data

import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.utils.Disposable
import com.gadarts.robots.GeneralUtils

class GameSessionDataRender : Disposable {
    val camera: PerspectiveCamera = GeneralUtils.createCamera(60F)

    override fun dispose() {
    }


}
