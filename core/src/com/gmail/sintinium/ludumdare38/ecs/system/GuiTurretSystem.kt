package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.planet.planetRadius
import com.gmail.sintinium.ludumdare38.planet.renderScale
import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.ui.TurretFactory
import com.gmail.sintinium.ludumdare38.ui.TurretHud
import com.gmail.sintinium.ludumdare38.util.PlanetMath

class GuiTurretSystem(var batch: SpriteBatch) : BaseSystem() {

    val turretHud = TurretHud(this)

    var selectedTurret: TurretFactory.TurretType? = null
        private set

    var selectedTurretTexture: TextureRegion? = null
    var xOffset = 0f
    var yOffset = 0f
    var wasDown = false

    override fun processSystem() {
        batch.end()
        turretHud.batch.projectionMatrix = gameScreen.hudCamera.combined
        turretHud.batch.begin()
        val hudPressed = turretHud.render()
        var canAfford = false

        if (selectedTurret != null && selectedTurretTexture != null) {
            game.font24.draw(turretHud.batch, "Right click to deselect turret", 0f, 24f)
            canAfford = selectedTurret!!.turretPrice <= gameScreen.coinBankSystem.coins
        }
        turretHud.batch.end()

        batch.begin()
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            selectedTurret = null
            selectedTurretTexture = null
        }

        val mousePos = gameScreen.worldCoordinates(Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat()))
        val angle = PlanetMath.angleFromPosition(mousePos)
        val distance2 = gameScreen.planet.centerPosition.dst2(mousePos)
        if (distance2 < planetRadius * planetRadius - 40f || Gdx.input.x > Gdx.graphics.width - TurretHud.sidebarWidth) return
        if (selectedTurret != null && selectedTurretTexture != null) {
            val position = PlanetMath.positionFromAngle(angle - 4.5f, selectedTurretTexture!!.regionHeight * renderScale)
            if (canAfford)
                batch.color = Color(1f, 1f, 1f, .5f)
            else
                batch.color = Color(1f, .2f, .2f, 1f)
//            batch.draw(selectedTurretTexture!!, position.x + (-xOffset * renderScale), position.y, xOffset * renderScale, 0f, selectedTurretTexture!!.regionWidth.toFloat() * renderScale, selectedTurretTexture!!.regionHeight.toFloat() * renderScale, 1f, 1f, angle)
            batch.draw(selectedTurretTexture!!.texture, position.x - selectedTurretTexture!!.regionWidth, position.y - selectedTurretTexture!!.regionHeight,
                    selectedTurretTexture!!.regionWidth.toFloat(), selectedTurretTexture!!.regionHeight.toFloat(),
                    selectedTurretTexture!!.regionWidth.toFloat(), selectedTurretTexture!!.regionHeight.toFloat(),
                    renderScale, renderScale, angle,
                    selectedTurretTexture!!.regionX, selectedTurretTexture!!.regionY, selectedTurretTexture!!.regionWidth, selectedTurretTexture!!.regionHeight,
                    angle > 180, false
            )
            batch.color = Color.WHITE
        }

        if (hudPressed) {
            wasDown = true
            return
        }

        if (selectedTurret != null && Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !wasDown && canAfford) {
            gameScreen.turretFactory.createTurretFromType(selectedTurret!!, angle)
            gameScreen.coinBankSystem.takeCoins(selectedTurret!!.turretPrice)
            selectedTurret = null
            selectedTurretTexture = null
            wasDown = true
        } else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            wasDown = false
        }
    }

    fun setSelected(turretType: TurretFactory.TurretType, texture: TextureRegion) {
        selectedTurret = turretType
        xOffset = texture.regionWidth / 2f
        yOffset = texture.regionHeight / 2f
        selectedTurretTexture = texture
    }

    fun onResize() {
        turretHud.onResize()
    }

}