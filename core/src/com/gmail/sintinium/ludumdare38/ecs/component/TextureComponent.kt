package com.gmail.sintinium.ludumdare38.ecs.component

import com.artemis.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TextureComponent : Component() {
    var texture: TextureRegion? = null
    var animation: Animation<TextureRegion>? = null

    var originX = 0f
    var originY = 0f
    var xOffset = 0f
    var yOffset = 0f
    var elapsedTime = 0f
}