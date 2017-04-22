package com.gmail.sintinium.ludumdare38.util

import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.planet.planetRadius
import com.gmail.sintinium.ludumdare38.screen.gameScreen

object PlanetMath {

    fun positionFromAngle(angle: Float, height: Float = 0f): Vector2 {
        val position = Vector2()
        position.x = (planetRadius + height)* Math.cos(Math.toRadians(angle.toDouble() + 90)).toFloat() + gameScreen.planet.position.x + planetRadius
        position.y = (planetRadius + height)* Math.sin(Math.toRadians(angle.toDouble() + 90)).toFloat() + gameScreen.planet.position.y + planetRadius
        return position
    }

}