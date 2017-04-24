package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.ecs.component.CoinDropComponent
import com.gmail.sintinium.ludumdare38.ecs.component.EnemyComponent
import com.gmail.sintinium.ludumdare38.ecs.component.PositionComponent
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.ui.TurretHud

@Wire
class CoreHealthSystem : IteratingSystem(Aspect.all(PositionComponent::class.java, EnemyComponent::class.java)) {

    lateinit var mPosition: ComponentMapper<PositionComponent>
    lateinit var mCoin: ComponentMapper<CoinDropComponent>

    val batch by lazy { gameScreen.hudBatch }

    var maxHealth = 100
    var health = maxHealth

    val healthbarTexture = game.textureAtlas.createPatch(Game.HEALTHBAR)
    var width = Gdx.graphics.width - TurretHud.sidebarWidth - 500f

    override fun process(entityId: Int) {
        val cPosition = mPosition[entityId]
        val cCoin = mCoin[entityId]
        if (cPosition.angle < 5 || cPosition.angle > 360 - 5) {
            health--
            cCoin?.shouldDrop = false
            world.delete(entityId)
        }
        gameScreen.batch.end()
        batch.projectionMatrix = gameScreen.hudCamera.combined
        val size = width * (health.toFloat() / maxHealth.toFloat())
        game.layout24.setText(game.font24, "$health")
        batch.begin()
        healthbarTexture.draw(batch, Gdx.graphics.width / 2 - size / 2f, Gdx.graphics.height - 50f, size, 25f)
        game.font24.draw(batch, game.layout24, Gdx.graphics.width / 2f - game.layout24.width / 2f, Gdx.graphics.height - 50f / 2f - game.layout24.height / 4f)
        batch.end()
        gameScreen.batch.begin()
        if (health < 0) {
//            game.screen = GameOverScreen()
        }

    }

}