package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.gmail.sintinium.ludumdare38.ecs.component.PositionComponent
import com.gmail.sintinium.ludumdare38.ecs.component.TextureComponent
import com.gmail.sintinium.ludumdare38.planet.renderScale
import com.gmail.sintinium.ludumdare38.util.PlanetMath

@Wire
class RenderSystem(var batch: SpriteBatch) : IteratingSystem(Aspect.all(TextureComponent::class.java, PositionComponent::class.java)) {

    lateinit var mPosition: ComponentMapper<PositionComponent>
    lateinit var mTexture: ComponentMapper<TextureComponent>

    override fun process(entityId: Int) {
        val cPosition = mPosition[entityId]
        val cTexture = mTexture[entityId]

        val position = PlanetMath.positionFromAngle(cPosition.angle, cPosition.height * renderScale)
        batch.draw(cTexture.texture, position.x + (cTexture.xOffset * renderScale), position.y + (cTexture.yOffset * renderScale), cTexture.originX * renderScale, cTexture.originY * renderScale, cTexture.texture.regionWidth.toFloat() * renderScale, cTexture.texture.regionHeight.toFloat() * renderScale, 1f, 1f, cPosition.angle)
    }

}