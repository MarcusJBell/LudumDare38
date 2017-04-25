package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.ecs.component.EnemyComponent
import com.gmail.sintinium.ludumdare38.ecs.component.HealthComponent
import com.gmail.sintinium.ludumdare38.ecs.component.PositionComponent
import com.gmail.sintinium.ludumdare38.ecs.component.TurretComponent
import com.gmail.sintinium.ludumdare38.turret.GuardianTurret
import com.gmail.sintinium.ludumdare38.util.PlanetMath

@Wire
class TurretShootSystem : IteratingSystem(Aspect.all(TurretComponent::class.java)) {

    lateinit var mTurret: ComponentMapper<TurretComponent>
    lateinit var mPosition: ComponentMapper<PositionComponent>
    lateinit var waveSystem: WaveSystem

    override fun process(entityId: Int) {
        if (waveSystem.currentWave == null) return
        val enemies = world.aspectSubscriptionManager[Aspect.all(PositionComponent::class.java, EnemyComponent::class.java, HealthComponent::class.java)].entities
        val cTurret = mTurret[entityId]
        cTurret.turret.waitingTime += Gdx.graphics.deltaTime

        if (cTurret.turret.waitingTime < cTurret.turret.shootDelay) return

        val angle = cTurret.turret.angle
        val dir = if (PlanetMath.isRightSide(angle)) -1f else 1f

        if (!cTurret.usesRange) {
            fire(cTurret, Vector2())
            return
        }

        for (i in 0..enemies.size() - 1) {
            val enemyId = enemies[i]
            val cPosition = mPosition[enemyId]
            if (cTurret.turret is GuardianTurret) {
                if (cPosition.angle <= cTurret.range || cPosition.angle >= 360 - cTurret.range) {
                    fire(cTurret, PlanetMath.positionFromAngle(cPosition.angle, cPosition.height + 50f))
                    break
                }
                continue
            }
            if (cTurret.sided) {
                if (!PlanetMath.isAngleBetween(cPosition.angle, angle, cTurret.range * dir)) {
                    continue
                }
            } else {
                if (!(PlanetMath.isAngleBetween(cPosition.angle, angle, angle + cTurret.range * -1) || PlanetMath.isAngleBetween(cPosition.angle, angle, angle + cTurret.range))) {
                    continue
                }
            }
            fire(cTurret, PlanetMath.positionFromAngle(cPosition.angle, cPosition.height + 50f))
            break
        }
    }

    fun fire(cTurret: TurretComponent, targetPosition: Vector2) {
        cTurret.turret.setTargetPosition(targetPosition)
        cTurret.turret.onFire()
        cTurret.turret.waitingTime -= cTurret.turret.shootDelay
    }

}