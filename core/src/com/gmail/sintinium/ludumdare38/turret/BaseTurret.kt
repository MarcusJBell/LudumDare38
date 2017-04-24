package com.gmail.sintinium.ludumdare38.turret

import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.ui.TurretFactory

abstract class BaseTurret(val turretType: TurretFactory.TurretType, var angle: Float) {

    var shootDelay = 5f
    var waitingTime = 0f

    abstract fun onFire()

    open fun setTargetPosition(position: Vector2) {}

}