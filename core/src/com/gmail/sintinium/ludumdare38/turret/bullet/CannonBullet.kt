package com.gmail.sintinium.ludumdare38.turret.bullet

import com.artemis.World
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.util.PlanetMath

class CannonBullet(world: World, entityId: Int, startAngle: Float, direction: Int) : BaseBullet(world, entityId, startAngle, 100f, direction, 2f) {

    val cannonballTexture = game.textureAtlas.findRegion(Game.CANNONBALL)!!
    val scale = 5f

    init {
        if (direction > 0)
            angle += 3
        else
            angle -= 7
    }

    override fun moveForward() {
        super.moveForward()
        if (direction > 0 && angle > 180) {
            world.delete(entityId)
        } else if (direction < 0 && angle < 180) {
            world.delete(entityId)
        }
    }

    override fun isColliding(angle: Float): Boolean {
        return PlanetMath.isAngleBetween(angle, this.angle - 5, this.angle + 5)
    }

    override fun onHit(): Boolean {
        world.delete(entityId)
        return true
    }

    override fun render(batch: SpriteBatch) {
        val position = PlanetMath.positionFromAngle(angle, 9f * scale)
        batch.draw(cannonballTexture.texture, position.x - cannonballTexture.regionWidth, position.y - cannonballTexture.regionHeight,
                cannonballTexture.regionWidth.toFloat(), cannonballTexture.regionHeight.toFloat(),
                cannonballTexture.regionWidth.toFloat(), cannonballTexture.regionHeight.toFloat(),
                scale, scale, angle,
                cannonballTexture.regionX, cannonballTexture.regionY, cannonballTexture.regionWidth, cannonballTexture.regionHeight,
                false, false
        )
    }

}