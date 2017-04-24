package com.gmail.sintinium.ludumdare38.turret.bullet

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.util.PlanetMath

class TeslaBullet(world: World, entityId: Int, startAngle: Float, speed: Float, direction: Int = 1) : BaseBullet(world, entityId, startAngle, speed, direction, 1f) {

    private val amountBeforeSpawn = 3.5f
    private var lastSpawnAmount = 0f
    private var fade = 1f
    var fadeRate = .8f

    private var segments = mutableListOf<TeslaSegment>()
    private val segmentAnimationTextures = game.textureAtlas.findRegions(Game.TESLA_BEAM)

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
        if (amountMoved >= 100) return
        super.moveForward()
        if (amountMoved - lastSpawnAmount > amountBeforeSpawn) {
            lastSpawnAmount = amountMoved
            segments.add(TeslaSegment(startAngle))
        }
        for (s in segments) {
            s.angle += direction * speed * Gdx.graphics.deltaTime
        }
    }

    override fun isColliding(angle: Float) : Boolean {
        return PlanetMath.isAngleBetween(angle, startAngle, this.angle)
    }

    override fun render(batch: SpriteBatch) {
        if (amountMoved >= 100) {
            batch.color = Color(1f, 1f, 1f, fade)
            fade -= fadeRate * Gdx.graphics.deltaTime
            if (fade <= 0) {
                world.delete(entityId)
                batch.color = Color.WHITE
                return
            }
        }
        for ((i, s) in segments.withIndex()) {
            s.elapsedTime += Gdx.graphics.deltaTime
            val texture = s.animation.getKeyFrame(s.elapsedTime, true)
            val position = PlanetMath.positionFromAngle(s.angle, texture.regionHeight / 2f)

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

    inner class TeslaSegment(var angle: Float) {
        var elapsedTime = 0f
        var animation = Animation<TextureRegion>(1/10f, segmentAnimationTextures, Animation.PlayMode.LOOP)
    }

}