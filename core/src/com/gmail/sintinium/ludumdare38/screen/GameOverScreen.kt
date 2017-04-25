package com.gmail.sintinium.ludumdare38.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.gmail.sintinium.ludumdare38.game

class GameOverScreen(val wave: Int) : Screen {

    val batch: SpriteBatch = SpriteBatch()
    var camera: OrthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    var viewport = ScreenViewport(camera)

    var startTime = System.currentTimeMillis()
    var waitTime = 3000f

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        if (System.currentTimeMillis() - startTime >= waitTime) {
            game.screen = GameScreen()
            return
        }

        batch.projectionMatrix = camera.combined
        batch.begin()
        game.layout24.setText(game.font24, "GAME OVER")
        game.font24.draw(batch, game.layout24, Gdx.graphics.width / 2f - game.layout24.width / 2f, Gdx.graphics.height / 2f + game.layout24.height / 2)

        game.layout16.setText(game.font16, "You made it to wave: $wave")
        game.font16.draw(batch, game.layout16, Gdx.graphics.width / 2f - game.layout16.width / 2f, Gdx.graphics.height / 2f + game.layout16.height / 2 - game.layout24.height - 10)
        val oldHeight = game.layout16.height

        game.layout16.setText(game.font16, "Restarting in... ${((waitTime -(System.currentTimeMillis() - startTime).toFloat()) / 1000f).toInt() + 1}")
        game.font16.draw(batch, game.layout16, Gdx.graphics.width / 2f - game.layout16.width / 2f, Gdx.graphics.height / 2f + game.layout16.height / 2 - game.layout24.height - oldHeight - 20)
        batch.end()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        camera.position.set(width / 2f, height / 2f, 0f)
        viewport.update(width, height, false)
    }

    override fun dispose() {
    }

}