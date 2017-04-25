package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.gmail.sintinium.ludumdare38.ecs.component.HealthComponent
import com.gmail.sintinium.ludumdare38.ecs.component.PositionComponent
import com.gmail.sintinium.ludumdare38.screen.gameScreen

@Wire
class HealthSystem : IteratingSystem(Aspect.all(HealthComponent::class.java, PositionComponent::class.java)) {

    lateinit var mHealth: ComponentMapper<HealthComponent>
    lateinit var soundSystem: SoundSystem

    override fun process(entityId: Int) {
        val cHp = mHealth[entityId]

        cHp.currentHealth -= cHp.currentDamageTaken
        cHp.currentDamageTaken = 0f

        if (cHp.currentHealth <= 0) {
            gameScreen.world.delete(entityId)
            soundSystem.playExplosion()
        }
    }
}