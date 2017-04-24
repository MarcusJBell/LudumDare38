package com.gmail.sintinium.ludumdare38.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.ecs.system.GuiTurretSystem
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.screen.gameScreen
import kotlin.properties.Delegates

class TurretHud(val turretSystem: GuiTurretSystem) {

    val batch by lazy { gameScreen.hudBatch }
    val debugTexture = game.textureAtlas.findRegion(Game.DEBUG)!!
    val tooltipHud = ShopTooltipHud()

    var buttons = mutableSetOf<TurrentButton>()
    private var xButton by Delegates.notNull<Float>()
    private var yButton by Delegates.notNull<Float>()
    private var wasDown = false

    companion object {
        val buttonSpacing = 10f
        val sidebarWidth = 200f
        val topPadding = 60f
    }

    val turretTextures = mutableMapOf<String, TextureRegion>()

    init {
        onResize()
        createButtons()
        for (i in TurretFactory.TurretType.values()) {
            turretTextures.put(i.textureName, game.textureAtlas.findRegion(i.textureName))
        }
    }

    fun createButtons() {
        for ((i, t) in TurretFactory.TurretType.values().withIndex()) {
            buttons.add(TurrentButton(Vector2(xButton - TurrentButton.buttonSize /2f, yButton - TurrentButton.buttonSize * i - (i * buttonSpacing) - (TurrentButton.buttonSize + buttonSpacing) - topPadding), t))
        }
    }

    /**
     * @return returns true if a gui element is clicked
     */
    fun render() : Boolean {
        var clicked = false
        batch.color = Color.DARK_GRAY

        batch.draw(debugTexture, Gdx.graphics.width - sidebarWidth, 0f, sidebarWidth, Gdx.graphics.height.toFloat())
        batch.color = Color.LIGHT_GRAY

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !wasDown) {
            for (b in buttons) {
                val click = b.onClick(gameScreen.hudWorldCoordinates(Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())))
                if (click != null) {
                    clicked = true
                    turretSystem.setSelected(b.turretType, turretTextures[b.turretType.textureName]!!)
                    break
                }
            }
            wasDown = true
        } else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            wasDown = false
        }


        for (b in buttons) {
            batch.draw(debugTexture, b.rectangle.x, b.rectangle.y, b.rectangle.width, b.rectangle.height)
            val texture = turretTextures[b.turretType.textureName]!!
            val scale = if (texture.regionHeight > texture.regionWidth) b.rectangle.height / texture.regionHeight else b.rectangle.width / texture.regionWidth
            val width = texture.regionWidth * scale
            val height = texture.regionHeight * scale
            batch.draw(texture, b.rectangle.x + b.rectangle.width / 2 - width / 2, b.rectangle.y, width, height)
        }
        showTooltip()

        batch.color = Color.WHITE
        return clicked
    }

    fun showTooltip() {
        for (b in buttons) {
            val hover = b.onClick(gameScreen.hudWorldCoordinates(Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())))
            if (hover != null) {
                tooltipHud.drawInformation(hover.turretName, hover.turretPrice, hover.description)
                break
            }
        }
    }

    fun onResize() {
        xButton = Gdx.graphics.width - sidebarWidth / 2f
        yButton = Gdx.graphics.height.toFloat()

        for ((i, b) in buttons.withIndex()) {
            b.rectangle.set(xButton - TurrentButton.buttonSize /2f, yButton - TurrentButton.buttonSize * i - (i * buttonSpacing) - (TurrentButton.buttonSize + buttonSpacing) - topPadding, b.rectangle.width, b.rectangle.height)
        }
    }

}