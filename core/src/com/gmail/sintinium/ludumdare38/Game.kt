package com.gmail.sintinium.ludumdare38

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.gmail.sintinium.ludumdare38.screen.GameScreen
import kotlin.properties.Delegates


var game by Delegates.notNull<Game>()

class Game : com.badlogic.gdx.Game() {

    lateinit var textureAtlas: TextureAtlas

    companion object {
        const val PLANET = "planet"
        const val CORE = "planetcore"
    }

    init {
        game = this
    }

    override fun create() {
        textureAtlas = TextureAtlas(Gdx.files.internal("assets/packed/textures.atlas"))
        setScreen(GameScreen())
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        super.render()
    }

    override fun dispose() {
        super.dispose()
        textureAtlas.dispose()
    }
}