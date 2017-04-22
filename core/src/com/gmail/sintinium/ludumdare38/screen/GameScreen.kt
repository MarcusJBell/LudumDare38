package com.gmail.sintinium.ludumdare38.screen

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.gmail.sintinium.ludumdare38.ecs.factory.EntityFactory
import com.gmail.sintinium.ludumdare38.ecs.system.RenderSystem
import com.gmail.sintinium.ludumdare38.planet.Planet
import kotlin.properties.Delegates

var gameScreen by Delegates.notNull<GameScreen>()

class GameScreen : Screen {

    var batch: SpriteBatch = SpriteBatch()
    var camera: OrthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    var viewport = ScreenViewport(camera)

    var planet = Planet(batch)

    var entityFactory = EntityFactory()
    var world: World = World(WorldConfigurationBuilder().with(
            RenderSystem(batch)
    ).build()).also { it.inject(entityFactory) }

    init {
        gameScreen = this
        entityFactory.createCore(world)
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        batch.projectionMatrix = camera.combined
        batch.begin()
        planet.render(delta)
        world.process()
        batch.end()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        camera.position.set(width / 2f, height / 2f, 0f)
        viewport.update(width, height, false)
        planet.resize()
    }

    override fun dispose() {
    }


}