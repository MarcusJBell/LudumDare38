package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.gmail.sintinium.ludumdare38.ecs.component.BulletComponent

@Wire
class BulletRenderSystem(var batch: SpriteBatch) : IteratingSystem(Aspect.all(BulletComponent::class.java)) {

    lateinit var mBullet: ComponentMapper<BulletComponent>

    override fun process(entityId: Int) {
        val cBullet = mBullet[entityId]
        cBullet.bullet.moveForward()
        cBullet.bullet.render(batch)
    }

}