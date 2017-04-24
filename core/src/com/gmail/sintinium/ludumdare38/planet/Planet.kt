package com.gmail.sintinium.ludumdare38.planet

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.game

var planetDiameter = 532f
var planetRadius = planetDiameter / 2f
var renderScale = 4f

class Planet(var batch: SpriteBatch) {

    var texture = game.textureAtlas.findRegion(Game.PLANET)!!
    var position = Vector2()
    var centerPosition = Vector2()

    fun resize() {
        position.set(Gdx.graphics.width / 2f - planetRadius, Gdx.graphics.height / 2f - planetRadius)
        centerPosition.set(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)
    }

    fun render(deltaTime: Float) {
        batch.draw(texture, position.x, position.y, planetDiameter, planetDiameter)
    }

}
