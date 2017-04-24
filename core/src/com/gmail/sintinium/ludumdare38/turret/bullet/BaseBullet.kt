package com.gmail.sintinium.ludumdare38.turret.bullet

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class BaseBullet(val world: World, val entityId: Int, var startAngle: Float, var speed: Float, var direction: Int, var damage: Float) {

    var angle = startAngle
    var amountMoved = 0f

    open fun moveForward() {
        angle += speed * direction * Gdx.graphics.deltaTime
        amountMoved += speed * Gdx.graphics.deltaTime
    }

    //should return true if bullet is destroyed
    abstract fun onHit(): Boolean

    abstract fun isColliding(angle: Float): Boolean

    abstract fun render(batch: SpriteBatch)

}