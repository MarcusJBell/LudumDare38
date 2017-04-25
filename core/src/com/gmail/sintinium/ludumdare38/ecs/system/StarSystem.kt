package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.planet.renderScale
import com.gmail.sintinium.ludumdare38.random
import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.util.PlanetMath

class StarSystem : BaseSystem() {

    var starTextures = listOf<TextureRegion>(game.textureAtlas.findRegion("Star1"), game.textureAtlas.findRegion("Star2"), game.textureAtlas.findRegion("Star3"),
            game.textureAtlas.findRegion("Star4"), game.textureAtlas.findRegion("Star5"))
    val colors = setOf(Color(119f / 255f, 187f / 255f, 215f / 255f, 1f), Color(250f / 255f, 232f / 255f, 135f / 255f, 1f))
    val fadeColor = Color(1f, 1f, 1f, 0f)
    private val stars = mutableListOf<Star>()
    val maxStars = 500

    val batch by lazy { gameScreen.hudBatch }

    val portalOpenTextures = game.textureAtlas.findRegions(Game.PORTALOPEN)
    val portalStayTextures = game.textureAtlas.findRegions(Game.PORTALSTAY)

    var portalAnimation: Animation<TextureRegion>? = null
    var elapsed = 0f

    init {
        for (i in 0..maxStars / 4) {
            spawnStar()
        }
    }

    override fun processSystem() {
        gameScreen.batch.end()
        batch.projectionMatrix = gameScreen.hudCamera.combined
        batch.begin()

        if (stars.size < maxStars) {
            if (stars.size < maxStars / 2) {
                if (random.nextDouble() > .8f)
                    spawnStar()
            } else if (random.nextDouble() > .95f) {
                spawnStar()
            }
        }
        val it = stars.iterator()
        while (it.hasNext()) {
            val star = it.next()
            if (System.currentTimeMillis() - star.startTime > star.liveTime) {
                it.remove()
            }
            star.color.a = 1f - (System.currentTimeMillis() - star.startTime).toFloat() / star.liveTime.toFloat()
            batch.color = star.color
            batch.draw(starTextures.elementAt(star.id), star.position.x, star.position.y)
            batch.color = Color.WHITE
        }

        batch.end()
        gameScreen.batch.begin()

        drawPortal()
        gameScreen.planet.render(Gdx.graphics.deltaTime)
    }

    fun drawPortal() {
        if (portalAnimation == null) return
        elapsed += Gdx.graphics.deltaTime
        val texture = portalAnimation!!.getKeyFrame(elapsed)
        val position = PlanetMath.positionFromAngle(180f, texture.regionHeight.toFloat() + 10f)
        val frame = portalAnimation!!.getKeyFrameIndex(elapsed)
        if (frame > 6 && portalAnimation!!.playMode == Animation.PlayMode.NORMAL) {
            portalAnimation = Animation(.075f, portalStayTextures, Animation.PlayMode.LOOP)
        }

        gameScreen.batch.draw(texture.texture, position.x - texture.regionWidth / 2f, position.y - texture.regionHeight,
                texture.regionWidth.toFloat() / 2f, texture.regionHeight.toFloat() / 2f,
                texture.regionWidth.toFloat(), texture.regionHeight.toFloat(),
                renderScale, renderScale, 180f,
                texture.regionX, texture.regionY, texture.regionWidth, texture.regionHeight,
                false, false
        )
    }

    fun openPortal() {
        elapsed = 0f
        portalAnimation = Animation(.075f, portalOpenTextures, Animation.PlayMode.NORMAL)
    }

    fun closePortal() {
        elapsed = 7 * .075f
        portalAnimation = Animation(.075f, portalOpenTextures, Animation.PlayMode.REVERSED)
    }

    fun spawnStar() {
        stars.add(Star(random.nextInt(5), Vector2(random.nextInt(Gdx.graphics.width).toFloat(), random.nextInt(Gdx.graphics.height).toFloat()), random.nextInt(5000) + 3000L, Color(colors.elementAt(random.nextInt(colors.size)))))
    }

    private inner class Star(val id: Int, val position: Vector2, val liveTime: Long, val color: Color) {
        var startTime = System.currentTimeMillis()
    }

}