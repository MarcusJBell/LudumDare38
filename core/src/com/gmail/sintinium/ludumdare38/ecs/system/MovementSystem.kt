package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.gmail.sintinium.ludumdare38.ecs.component.DirectionComponent
import com.gmail.sintinium.ludumdare38.ecs.component.PositionComponent
import com.gmail.sintinium.ludumdare38.ecs.component.TurretComponent

@Wire
class MovementSystem : IteratingSystem(Aspect.all(DirectionComponent::class.java, PositionComponent::class.java).exclude(TurretComponent::class.java)) {

    lateinit var mDirection: ComponentMapper<DirectionComponent>
    lateinit var mPosition: ComponentMapper<PositionComponent>

    override fun process(entityId: Int) {
        val cDir = mDirection[entityId]
        val cPos = mPosition[entityId]
        cPos.angle += cDir.direction * Gdx.graphics.deltaTime
        if (cPos.angle < 0) {
            cPos.angle += 360
        }
        if (cPos.angle > 360) {
            cPos.angle %= 360
        }
    }
}