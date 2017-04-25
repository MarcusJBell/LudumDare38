package com.gmail.sintinium.ludumdare38.turret.bullet

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.random
import com.gmail.sintinium.ludumdare38.util.PlanetMath

class QuakeBullet(world: World, entityId: Int, startAngle: Float, speed: Float, direction: Int = 1) : BaseBullet(world, entityId, startAngle, speed, direction, .1f) {

    private val amountBeforeSpawn = 2f
    private var lastSpawnAmount = 0f
    private var fade = 1f
    var fadeRate = .11f

    private var segments = mutableListOf<QuakeSegment>()
    private val segmentAnimationTexture = game.textureAtlas.findRegion(Game.QUAKE_WAVE)

    override fun moveForward() {
        var oldSpeed = speed
        var moved = 0f
        speed = .1f
        while (moved < oldSpeed) {
            moved += speed
            move()
        }
        speed = oldSpeed - moved
        move()
        speed = oldSpeed
    }

    override fun onHit(): Boolean {
        return false
    }

    private fun move() {
        if (amountMoved >= 90) return
        super.moveForward()
        if (amountMoved - lastSpawnAmount > amountBeforeSpawn) {
            lastSpawnAmount = amountMoved
            segments.add(QuakeSegment(startAngle))
        }
        for (s in segments) {
            s.angle += direction * speed * Gdx.graphics.deltaTime
        }
    }

    override fun isColliding(angle: Float): Boolean {
        return PlanetMath.isAngleBetween(angle, startAngle, this.angle)
    }

    override fun render(batch: SpriteBatch) {
        if (amountMoved >= 90) {
            batch.color = Color(1f, 1f, 1f, fade)
            fade -= fadeRate * Gdx.graphics.deltaTime
            if (fade <= 0) {
                world.delete(entityId)
                batch.color = Color.WHITE
                return
            }
        }
        for ((i, s) in segments.withIndex()) {
            s.elapsedTime += Gdx.graphics.deltaTime * 5
            val texture = s.texture
            val position = PlanetMath.positionFromAngle(s.angle, texture.regionHeight / 2f + (4f * (Math.sin(s.elapsedTime.toDouble()).toFloat())))

            batch.draw(texture.texture, position.x - texture.regionWidth, position.y - texture.regionHeight,
                    texture.regionWidth.toFloat(), texture.regionHeight.toFloat(),
                    texture.regionWidth.toFloat(), texture.regionHeight.toFloat(),
                    1f, 1f, s.angle,
                    texture.regionX, texture.regionY, texture.regionWidth, texture.regionHeight,
                    false, false
            )
        }
        batch.color = Color.WHITE
    }

    inner class QuakeSegment(var angle: Float) {
        var elapsedTime = random.nextFloat() + random.nextInt(360)
        var texture = segmentAnimationTexture
    }

}