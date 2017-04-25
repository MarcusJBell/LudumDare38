package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.BaseSystem
import com.artemis.annotations.Wire
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.ui.TurretHud

@Wire
class NextWaveSystem : BaseSystem() {

    lateinit var waveSystem: WaveSystem

    val batch by lazy { gameScreen.hudBatch }
    val texture = game.textureAtlas.createPatch(Game.UI)
    val playButton = game.textureAtlas.findRegion(Game.PLAYBUTTON)

    val width = TurretHud.sidebarWidth - 25f
    var height = 75f
    var blinkTimer = System.currentTimeMillis()
    var breakTimer = System.currentTimeMillis()

    override fun processSystem() {
        height = 50f
        val canClick = waveSystem.currentWave == null
        val position = Vector2(Gdx.graphics.width - width, height)

        gameScreen.batch.end()
        batch.projectionMatrix = gameScreen.hudCamera.combined
        batch.begin()

        val rectangle = Rectangle(position.x - 25f / 2f, position.y - height + 10, width, height)
        val highlight = rectangle.contains(gameScreen.hudWorldCoordinates(Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())))
        var blink = false

        if (System.currentTimeMillis() - blinkTimer < 500) {
            blink = true
            breakTimer = System.currentTimeMillis()
        }
        if (System.currentTimeMillis() - breakTimer > 500) {
            blinkTimer = System.currentTimeMillis()
        }

        if (canClick) {
            if (blink && !highlight) {
                batch.color = Color(1f, .5f, .5f, 1f)
            } else {
                batch.color = if (highlight) Color(1f, 1f, 1f, 1f) else Color(.8f, .8f, .8f, 1f)
            }
        } else {
            batch.color = Color(.8f, .5f, .5f, .8f)
        }

        texture.draw(batch, position.x - 25f / 2f, position.y - height + 10, width, height)
        game.font16.color = batch.color
        game.layout16.setText(game.font16, "Next Wave")
        game.font16.draw(batch, game.layout16, position.x - 25f / 2f + width / 2 - game.layout16.width / 2 - playButton.regionWidth, position.y - height / 2f + game.layout16.height / 2 + 10)

        game.font16.color = Color.WHITE
        game.layout16.setText(game.font16, "Wave: ${waveSystem.waveNumber}")
        game.font16.draw(batch, game.layout16, Gdx.graphics.width - TurretHud.sidebarWidth / 2 - game.layout16.width / 2, position.y + game.layout16.height + 10 + 5)

        batch.draw(playButton,  position.x - 25f / 2f + width / 2 + game.layout16.width / 2f + 10, position.y - height / 2f + 10 - playButton.regionHeight / 2f)

        if (canClick && Gdx.input.isButtonPressed(Input.Buttons.LEFT) && highlight) {
            waveSystem.nextWave()
        }

        batch.color = Color.WHITE
        batch.end()
        gameScreen.batch.begin()
    }

}