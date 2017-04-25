package com.gmail.sintinium.ludumdare38

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.gmail.sintinium.ludumdare38.screen.GameScreen
import java.io.PrintWriter
import java.util.*
import kotlin.properties.Delegates
import com.sun.deploy.uitoolkit.ToolkitStore.dispose
import com.badlogic.gdx.graphics.PixmapIO
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.utils.BufferUtils
import com.badlogic.gdx.utils.ScreenUtils




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
        const val BLORB = "Blorb"
        const val CORE = "planetcore"
        const val TESLA_BEAM = "Teslabeam"
        const val QUAKE_WAVE = "Seismicwave"
        const val SPIKES = "Spikeanimation"
        const val CANNONBALL = "Cannonball"
        const val EYEBULLET = "Eyeturretbullet"
        const val COIN = "Coin"
        const val COIN_SHEEN = "Coin_Sheen"
        const val HEALTHBAR = "Healthbar"
        const val UI = "UserInterface"
        const val PLAYBUTTON = "Playbutton"
        const val PORTALOPEN = "Portal_Portal"
        const val PORTALSTAY = "Portalstay"
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

//        Thread.setDefaultUncaughtExceptionHandler(object : Thread.UncaughtExceptionHandler {
//            override fun uncaughtException(t: Thread, e: Throwable) {
//                val writer = PrintWriter("ERROR.log", "UTF-8")
//                writer.println(e.message)
//                writer.close()
//            }
//        })
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        super.render()
        if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) {
            val pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.backBufferWidth, Gdx.graphics.backBufferHeight, true)
            val pixmap = Pixmap(Gdx.graphics.backBufferWidth, Gdx.graphics.backBufferHeight, Pixmap.Format.RGBA8888)
            BufferUtils.copy(pixels, 0, pixmap.pixels, pixels.size)
            PixmapIO.writePNG(Gdx.files.absolute("D:/Users/sinti/Documents/Projects/LibGDX/LudumDare38/screenshot.png"), pixmap)
            pixmap.dispose()
        }
    }

    override fun dispose() {
        super.dispose()
        textureAtlas.dispose()
    }
}