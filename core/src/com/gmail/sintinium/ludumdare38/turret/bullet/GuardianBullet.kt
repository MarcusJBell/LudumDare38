package com.gmail.sintinium.ludumdare38.turret.bullet

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.util.PlanetMath

class GuardianBullet(world: World, entityId: Int, startAngle: Float, var targetPosition: Vector2, height: Float) : BaseBullet(world, entityId, startAngle, 500f, 1, 500f) {

    private val texture = game.textureAtlas.findRegion(Game.EYEBULLET)
    private var position = PlanetMath.positionFromAngle(angle, height + 70f)
    private var bulletAngle = PlanetMath.angleBetweenPoints(position, targetPosition) + 90

    override fun moveForward() {
        val vel = Vector2(targetPosition.x - position.x, targetPosition.y - position.y)
        position.add(vel.nor().scl(speed * Gdx.graphics.deltaTime))
        if (Math.abs(position.dst2(targetPosition)) <= 16f) {
            world.delete(entityId)
        }
    }

    override fun onHit(): Boolean {
        world.delete(entityId)
        return true
    }

    override fun isColliding(angle: Float): Boolean {
        val bAngle = PlanetMath.angleFromPosition(position)
        if (angle > 180 && bAngle < 180) return false
        val hit = PlanetMath.isAngleBetween(angle, bAngle - 5, bAngle + 5)
        return hit
    }

    override fun render(batch: SpriteBatch) {
        batch.draw(texture, position.x - texture.regionWidth / 2f, position.y, texture.regionWidth.toFloat() / 2f, texture.regionHeight.toFloat() / 2f, texture.regionWidth.toFloat(), texture.regionHeight.toFloat(), 3f, 3f, bulletAngle)
//        batch.draw(texture.texture, position.x - texture.regionWidth / 2, position.y - texture.regionHeight / 2f,
//                texture.regionWidth.toFloat() / 2, texture.regionHeight.toFloat() / 2,
//                texture.regionWidth.toFloat(), texture.regionHeight.toFloat(),
//                3f, 3f, bulletAngle,
//                texture.regionX, texture.regionY, texture.regionWidth, texture.regionHeight,
//                false, false
//        )
    }

}