package com.gmail.sintinium.ludumdare38.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.screen.gameScreen

class ShopTooltipHud {

    val batch by lazy { gameScreen.hudBatch }
    val texture = game.textureAtlas.findRegion(Game.DEBUG)
    val width = 350f
    val height = 200f

    fun drawInformation(turretName: String, price: Int, descrption: String) {
        batch.color = Color(.1f, .1f, .1f, 1f)
        val position = gameScreen.hudWorldCoordinates(Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat()))
        game.layout16.setText(game.font16, "$turretName\n\$$price\nDescription: $descrption", Color.WHITE, width, Align.left, true)
        batch.draw(texture, position.x - width - 10f, position.y - game.layout16.height - 9f, width + 10f, game.layout16.height + 10f)
        game.font16.draw(batch, game.layout16, position.x - width - 5f, position.y - 5f)
    }

}