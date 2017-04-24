package com.gmail.sintinium.ludumdare38.ui

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class TurrentButton(position: Vector2, val turretType: TurretFactory.TurretType) {

    companion object {
        const val buttonSize = 50f
    }

    val rectangle = Rectangle(position.x, position.y, buttonSize, buttonSize)

    fun isClicked(position: Vector2) = rectangle.contains(position)

    fun onClick(position: Vector2) : TurretFactory.TurretType? {
        if (!isClicked(position)) return null
        return turretType
    }

}