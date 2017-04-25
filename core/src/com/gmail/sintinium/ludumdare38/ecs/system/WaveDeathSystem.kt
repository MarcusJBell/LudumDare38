package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.annotations.Wire
import com.gmail.sintinium.ludumdare38.ecs.component.EnemyComponent

@Wire
class WaveDeathSystem : BaseEntitySystem(Aspect.all(EnemyComponent::class.java)) {

    lateinit var waveSystem: WaveSystem

    override fun processSystem() {
    }

    override fun removed(entityId: Int) {
        waveSystem.enemyDestroyed()
    }
}