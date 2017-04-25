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
import com.gmail.sintinium.ludumdare38.screen.GameOverScreen
import com.gmail.sintinium.ludumdare38.ui.TurretHud

@Wire
class CoreHealthSystem : IteratingSystem(Aspect.all(PositionComponent::class.java, EnemyComponent::class.java)) {

    lateinit var mPosition: ComponentMapper<PositionComponent>
    lateinit var mCoin: ComponentMapper<CoinDropComponent>
    lateinit var soundSystem: SoundSystem
    lateinit var waveSystem: WaveSystem

//    var maxHealth = -1
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
            soundSystem.coreExplosion()
        }

        if (health < 0) {
            game.screen = GameOverScreen(waveSystem.waveNumber)
        }

    }

}