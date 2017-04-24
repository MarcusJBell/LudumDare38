package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.gmail.sintinium.ludumdare38.ecs.component.DirectionComponent
import com.gmail.sintinium.ludumdare38.ecs.component.PositionComponent
import com.gmail.sintinium.ludumdare38.ecs.component.TextureComponent
import com.gmail.sintinium.ludumdare38.planet.renderScale
import com.gmail.sintinium.ludumdare38.util.PlanetMath

@Wire
class RenderSystem(var batch: SpriteBatch) : IteratingSystem(Aspect.all(TextureComponent::class.java, PositionComponent::class.java)) {

    lateinit var mPosition: ComponentMapper<PositionComponent>
    lateinit var mTexture: ComponentMapper<TextureComponent>
    lateinit var mDirection: ComponentMapper<DirectionComponent>

    var tempEntities = mutableSetOf<Int>()

    override fun end() {
        for (entityId in tempEntities.sortedBy { mPosition[it].zOrder }) {
            val cPosition = mPosition[entityId]
            val cTexture = mTexture[entityId]
            val cDirection = mDirection[entityId]

            val position = PlanetMath.positionFromAngle(cPosition.angle, cPosition.height * renderScale)
            cTexture.elapsedTime += Gdx.graphics.deltaTime
            val texture = cTexture.animation?.getKeyFrame(cTexture.elapsedTime, true) ?: cTexture.texture!!

            var flip = false
            if (cDirection != null && cDirection.direction < 0) {
                flip = true
            }

            batch.draw(texture.texture, position.x + (cTexture.xOffset * renderScale), position.y + (cTexture.yOffset * renderScale),
                    cTexture.originX * renderScale, cTexture.originY * renderScale,
                    texture.regionWidth.toFloat() * renderScale, texture.regionHeight.toFloat() * renderScale,
                    1f, 1f, cPosition.angle,
                    texture.regionX, texture.regionY, texture.regionWidth, texture.regionHeight,
                    flip, false
            )
        }
    }

    override fun removed(entityId: Int) {
        tempEntities.remove(entityId)
    }

    override fun process(entityId: Int) {
        tempEntities.add(entityId)
    }

}