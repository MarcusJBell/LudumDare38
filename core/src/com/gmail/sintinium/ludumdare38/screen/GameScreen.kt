package com.gmail.sintinium.ludumdare38.screen

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.gmail.sintinium.ludumdare38.ecs.factory.EntityFactory
import com.gmail.sintinium.ludumdare38.ecs.system.*
import com.gmail.sintinium.ludumdare38.planet.Planet
import com.gmail.sintinium.ludumdare38.planet.planetRadius
import com.gmail.sintinium.ludumdare38.planet.renderScale
import com.gmail.sintinium.ludumdare38.ui.TurretFactory
import kotlin.properties.Delegates

var gameScreen by Delegates.notNull<GameScreen>()

class GameScreen : Screen {

    var batch: SpriteBatch = SpriteBatch()
    var camera: OrthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    var hudCamera: OrthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    var viewport = ScreenViewport(camera)
    var hudViewport = ScreenViewport(hudCamera)

    var hudBatch = SpriteBatch().also { it.projectionMatrix = hudCamera.projection }
    var shapeRenderer = ShapeRenderer().also { it.projectionMatrix = hudCamera.projection }

    var planet = Planet(batch)

    var guiTurretSystem = GuiTurretSystem(batch)
    var coinBankSystem = CoinBankSystem()
    var coreHealthSystem = CoreHealthSystem()
    var soundSystem = SoundSystem()

    var world: World = World(WorldConfigurationBuilder().with(
            WaveSystem(),
            StarSystem(),
            MovementSystem(),
            TurretShootSystem(),
            RenderSystem(batch),
            BulletRenderSystem(batch),
            DamageSystem(),
            HealthSystem(),
            WaveDeathSystem(),
            coreHealthSystem,
            CoreHealthRenderSystem(),
            guiTurretSystem,
            NextWaveSystem(),
            CoinDropSystem(),
            soundSystem,
            coinBankSystem
    ).build())

    var entityFactory = EntityFactory()
    var turretFactory = TurretFactory(world, entityFactory)

    init {
        world.inject(entityFactory)
        gameScreen = this
        entityFactory.createCore(world)
        camera.zoom += .2f
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        batch.projectionMatrix = camera.combined
        batch.begin()
        world.process()
        batch.end()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        camera.zoom = (planetRadius * renderScale + 100f) /  height.toFloat()
        camera.position.set(width / 2f, height / 2f, 0f)
        viewport.update(width, height, false)

        hudCamera.position.set(width / 2f, height / 2f, 0f)
        hudViewport.update(width, height, false)

        planet.resize()
        guiTurretSystem.onResize()
    }

    override fun dispose() {
        soundSystem.disposeMusic()
    }

    fun worldCoordinates(screenPosition: Vector2): Vector2 {
        val vec3 = camera.unproject(Vector3(screenPosition, 0f))
        return Vector2(vec3.x, vec3.y)
    }

    fun hudWorldCoordinates(screenPosition: Vector2): Vector2 {
        val vec3 = hudCamera.unproject(Vector3(screenPosition, 0f))
        return Vector2(vec3.x, vec3.y)
    }

}