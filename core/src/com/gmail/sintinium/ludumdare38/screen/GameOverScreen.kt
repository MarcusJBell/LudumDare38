package com.gmail.sintinium.ludumdare38.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.gmail.sintinium.ludumdare38.game

class GameOverScreen : Screen {

    val batch: SpriteBatch = SpriteBatch()
    var camera: OrthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    var viewport = ScreenViewport(camera)

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        batch.projectionMatrix = camera.combined
        batch.begin()
        game.layout24.setText(game.font24, "GAME OVER")
        game.font24.draw(batch, game.layout24, Gdx.graphics.width / 2f - game.layout24.width / 2f, Gdx.graphics.height / 2f + game.layout24.height / 2)
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