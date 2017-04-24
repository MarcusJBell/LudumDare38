package com.gmail.sintinium.ludumdare38

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.gmail.sintinium.ludumdare38.screen.GameScreen
import java.util.*
import kotlin.properties.Delegates


var game by Delegates.notNull<Game>()
val random = Random()

class Game : com.badlogic.gdx.Game() {

    lateinit var textureAtlas: TextureAtlas
    lateinit var font24: BitmapFont
    lateinit var font16: BitmapFont
    lateinit var layout24: GlyphLayout
    lateinit var layout16: GlyphLayout

    companion object {
        const val DEBUG = "core"
        const val PLANET = "planet"
        const val BLOB = "Blobenemy_Pulsing"
        const val PURPLE_BLOB = "Purpleblob"
        const val CORE = "planetcore"
        const val TESLA_BEAM = "Teslabeam"
        const val SPIKES = "Spikeanimation"
        const val CANNONBALL = "Cannonball"
        const val EYEBULLET = "Eyeturretbullet"
        const val COIN = "Coin"
        const val COIN_SHEEN = "Coin_Sheen"
        const val HEALTHBAR = "Healthbar"
    }

    init {
        game = this
    }

    fun initFont() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Ubuntu.ttf"))
        val params = FreeTypeFontGenerator.FreeTypeFontParameter()
        params.size = 24
        params.color = Color.WHITE
        font24 = generator.generateFont(params)
        params.size = 16
        font16 = generator.generateFont(params)
        generator.dispose()
        layout24 = GlyphLayout()
        layout16 = GlyphLayout()
    }

    override fun create() {
        initFont()
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