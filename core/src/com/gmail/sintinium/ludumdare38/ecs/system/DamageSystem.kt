package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.gmail.sintinium.ludumdare38.ecs.component.BulletComponent
import com.gmail.sintinium.ludumdare38.ecs.component.EnemyComponent
import com.gmail.sintinium.ludumdare38.ecs.component.HealthComponent
import com.gmail.sintinium.ludumdare38.ecs.component.PositionComponent

//class DamageSystem : IteratingSystem(Aspect.all(HealthComponent::class.java, PositionComponent::class.java, EnemyComponent::class.java)) {
class DamageSystem : IteratingSystem(Aspect.all(BulletComponent::class.java)) {

    lateinit var mHealth: ComponentMapper<HealthComponent>
    lateinit var mPosition: ComponentMapper<PositionComponent>
    lateinit var mBullet: ComponentMapper<BulletComponent>

//    override fun process(entityId: Int) {
//        val bullets = world.aspectSubscriptionManager[Aspect.all(BulletComponent::class.java)].entities
//        val cHealth = mHealth[entityId]
//        val cPosition = mPosition[entityId]
//
//        for (i in 0..bullets.size() - 1) {
//            val bulletId = bullets[i]
//            val cBullet = mBullet[bulletId]
//            val isColliding = cBullet.bullet.isColliding(cPosition.angle)
//            if (isColliding) {
//                val damage = cBullet.bullet.damage * if (cBullet.damageOverTime) Gdx.graphics.deltaTime else 1f
//                cHealth.damage(damage)
//                val destroyed = cBullet.bullet.onHit()
//                if (destroyed) {
//                    println("RETURNING $entityId")
//                    return
//                }
//            }
//        }
//    }

    override fun process(entityId: Int) {
        val enemies = world.aspectSubscriptionManager[Aspect.all(HealthComponent::class.java, PositionComponent::class.java, EnemyComponent::class.java)].entities
        val cBullet = mBullet[entityId]

        loop@ for (i in 0..enemies.size() - 1) {
            val enemyId = enemies[i]
            val cHealth = mHealth[enemyId]
            val cPosition = mPosition[enemyId]
            val isColliding = cBullet.bullet.isColliding(cPosition.angle)
            if (isColliding) {
                val damage = cBullet.bullet.damage * if (cBullet.damageOverTime) Gdx.graphics.deltaTime else 1f
                cHealth.damage(damage)
                val destroyed = cBullet.bullet.onHit()
                if (destroyed) {
                    break@loop
                }
            }
        }
    }

}