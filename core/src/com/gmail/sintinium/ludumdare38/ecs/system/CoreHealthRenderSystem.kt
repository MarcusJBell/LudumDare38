package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.BaseSystem
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.screen.gameScreen

@Wire
class CoreHealthRenderSystem : BaseSystem() {

    val batch by lazy { gameScreen.hudBatch }

    lateinit var hpSystem: CoreHealthSystem

    override fun processSystem() {
        val width = hpSystem.width
        val health = hpSystem.health
        val maxHealth = hpSystem.maxHealth

        gameScreen.batch.end()

        batch.projectionMatrix = gameScreen.hudCamera.combined
        val size = width * (health.toFloat() / maxHealth.toFloat())
        game.layout24.setText(game.font24, "$health")
        batch.begin()
        hpSystem.healthbarTexture.draw(batch, Gdx.graphics.width / 2 - size / 2f, Gdx.graphics.height - 50f, size, 25f)
        game.font24.draw(batch, game.layout24, Gdx.graphics.width / 2f - game.layout24.width / 2f, Gdx.graphics.height - 50f / 2f - game.layout24.height / 4f)
        batch.end()

        gameScreen.batch.begin()
    }
}