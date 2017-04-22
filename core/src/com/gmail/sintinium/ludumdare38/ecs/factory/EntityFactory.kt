package com.gmail.sintinium.ludumdare38.ecs.factory

import com.artemis.ComponentMapper
import com.artemis.World
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.ecs.component.PositionComponent
import com.gmail.sintinium.ludumdare38.ecs.component.TextureComponent
import com.gmail.sintinium.ludumdare38.game

class EntityFactory {

    lateinit var mPosition: ComponentMapper<PositionComponent>
    lateinit var mTexture: ComponentMapper<TextureComponent>

    fun createCore(world: World): Int {
        var e = world.create()
        mPosition.create(e).also {
            it.height = -15f
        }
        mTexture.create(e).also {
            it.texture = game.textureAtlas.findRegion(Game.CORE)
            it.originX = it.texture.regionWidth / 2f
            it.xOffset = -it.texture.regionWidth / 2f
        }
        return e
    }

}