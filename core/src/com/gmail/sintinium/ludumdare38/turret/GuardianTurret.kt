package com.gmail.sintinium.ludumdare38.turret

import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.ui.TurretFactory

class GuardianTurret(angle: Float) : BaseTurret(TurretFactory.TurretType.GUARDIAN, angle) {

    var bulletPos = Vector2()

    companion object {
        const val RANGE = 30f
        const val HEIGHT = 20f
    }

    override fun onFire() {
        gameScreen.entityFactory.createGuardianBullet(gameScreen.world, angle, Vector2(bulletPos), HEIGHT)
    }

    override fun setTargetPosition(position: Vector2) {
        bulletPos.set(position)
    }
}