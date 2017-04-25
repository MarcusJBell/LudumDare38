package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.ui.TurretHud

class CoinBankSystem : BaseSystem() {

    var coins = 1000f
        private set

    val batch by lazy { gameScreen.hudBatch }

    private var pendingCoins = 0f
    private var lastPickup = -1L
    private var firstPickup = -1L
    private var addingPickup = false
    private var roundAmount = -1f
    private var roundTime = -1L

    private val coinTextures = game.textureAtlas.findRegions(Game.COIN_SHEEN)
    private val coinAnimation = Animation<TextureRegion>(1 / 10f, coinTextures, Animation.PlayMode.NORMAL)
    private val coinTexture = game.textureAtlas.findRegion(Game.COIN)

    var elapsedTime = 0f
    var lastShimmer = -1L

    override fun processSystem() {
        if (!addingPickup && (System.currentTimeMillis() - lastPickup >= 1000 || System.currentTimeMillis() - firstPickup > 3000)) {
            lastPickup = -1L
            firstPickup = -1L
            addingPickup = true
            roundAmount = -1f
            roundTime = -1L
        }

        if (addingPickup) {
            if (roundAmount == -1f || System.currentTimeMillis() - roundTime > 3000L) {
                roundAmount = pendingCoins
                roundTime = System.currentTimeMillis()
            }
            val addAmount = roundAmount * Gdx.graphics.deltaTime
            coins += addAmount
            pendingCoins -= addAmount
            if (pendingCoins <= 0) {
                addingPickup = false
                coins -= Math.abs(pendingCoins)
                pendingCoins = 0f
            }
        }

        gameScreen.batch.end()
        batch.projectionMatrix = gameScreen.hudCamera.combined
        batch.begin()
        renderCoins()
        batch.end()
        gameScreen.batch.begin()
    }

    fun renderCoins() {
        val coinX = Gdx.graphics.width - TurretHud.sidebarWidth + 20f
        val coinY = Gdx.graphics.height - 10f

        if (lastShimmer != -1L) {
            batch.draw(coinTexture, coinX, coinY - coinTexture.regionHeight)
            if (System.currentTimeMillis() - lastShimmer > 5000) {
                elapsedTime = 0f
                lastShimmer = -1L
            }
        } else {
            elapsedTime += Gdx.graphics.deltaTime
            val coinTexture = coinAnimation.getKeyFrame(elapsedTime, false)
            batch.draw(coinTexture, coinX, coinY - coinTexture.regionHeight)
            if (coinAnimation.isAnimationFinished(elapsedTime)) {
                lastShimmer = System.currentTimeMillis()
            }
        }

        game.font24.draw(batch, "${coins.toInt()}", coinX + coinTexture.regionWidth + 5f, coinY)
        game.font24.color = Color.GRAY
        if (pendingCoins >= 1)
            game.font24.draw(batch, "+${pendingCoins.toInt()}", coinX + coinTexture.regionWidth - 8f, coinY - game.font24.xHeight - 7)
        game.font24.color = Color.WHITE
    }

    fun addCoins(amount: Int) {
        if (firstPickup == -1L) {
            firstPickup = System.currentTimeMillis()
        }
        lastPickup = System.currentTimeMillis()
        pendingCoins += amount
    }

    fun takeCoins(amount: Int) {
        if (coins + pendingCoins < amount) return
        coins -= amount
    }

}