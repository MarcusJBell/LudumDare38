package com.gmail.sintinium.ludumdare38.util

import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.planet.planetRadius
import com.gmail.sintinium.ludumdare38.screen.gameScreen

object PlanetMath {

    fun positionFromAngle(angle: Float, height: Float = 0f): Vector2 {
        val position = Vector2()
        position.x = (planetRadius + height) * Math.cos(Math.toRadians(angle.toDouble() + 90)).toFloat() + gameScreen.planet.position.x + planetRadius
        position.y = (planetRadius + height) * Math.sin(Math.toRadians(angle.toDouble() + 90)).toFloat() + gameScreen.planet.position.y + planetRadius
        return position
    }

    fun angleFromPosition(position: Vector2): Float {
        var angle = Math.toDegrees(Math.atan2((position.y - (gameScreen.planet.position.y + planetRadius)).toDouble(), (position.x - (gameScreen.planet.position.x + planetRadius)).toDouble())).toFloat() - 90
        angle %= 360
        if (angle < 0) {
            angle += 360
        }
        return angle
    }

    fun angleBetweenPoints(position: Vector2, position2: Vector2): Float {
        var angle = Math.toDegrees(Math.atan2((position2.y - position.y).toDouble(), (position2.x - position.x).toDouble())).toFloat() - 90
        angle %= 360
        if (angle < 0) {
            angle += 360
        }
        return angle
    }

    fun isAngleBetween(target: Float, angle1: Float, angle2: Float): Boolean {
        val a1 = Math.min(angle1, angle2)
        val a2 = Math.max(angle1, angle2)
        return target >= a1 && target <= a2
    }

    fun isRightSide(angle: Float): Boolean {
        return angle > 180
    }

}