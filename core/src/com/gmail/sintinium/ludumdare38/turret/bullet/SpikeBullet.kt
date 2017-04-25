package com.gmail.sintinium.ludumdare38.turret.bullet

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.random
import com.gmail.sintinium.ludumdare38.util.PlanetMath

class SpikeBullet(world: World, entityId: Int, startAngle: Float, var range: Float) : BaseBullet(world, entityId, startAngle, 0f, 1, 2.5f) {

    private val spikeTextures = game.textureAtlas.findRegions(Game.SPIKES)
    private val spikes = mutableListOf<Spike>()

    init {
        for (i in 0..10) {
            val min = angle - range
            val max = angle + range
            val angle = random.nextInt((max.toInt() - min.toInt()) + 1) + min
            spikes.add(Spike(angle))
        }
    }

    override fun isColliding(angle: Float): Boolean {
        return PlanetMath.isAngleBetween(angle, startAngle - range, startAngle + range)
    }

    override fun onHit(): Boolean {
        return false
    }

    override fun render(batch: SpriteBatch) {
        var done = false
        for ((i, s) in spikes.withIndex()) {
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
            if (!done) {
                done = s.animation.isAnimationFinished(s.elapsedTime)
            }
        }
        if (done) {
            world.delete(entityId)
        }
    }

    inner class Spike(var angle: Float) {
        var elapsedTime = 0f
        var animation = Animation<TextureRegion>(1/10f, spikeTextures, Animation.PlayMode.LOOP)
    }

}