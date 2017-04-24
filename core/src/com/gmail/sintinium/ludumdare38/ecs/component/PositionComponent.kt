package com.gmail.sintinium.ludumdare38.ecs.component

import com.artemis.Component

class PositionComponent : Component() {
    private var _angle = 0f
    var angle: Float
    get() = _angle
    set(value) { _angle = value % 360 }

    var height = 0f
    var zOrder = 0f
}