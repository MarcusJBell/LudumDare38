package com.gmail.sintinium.ludumdare38.turret

import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.ui.TurretFactory

class CannonTurret(angle: Float) : BaseTurret(TurretFactory.TurretType.CANNON, angle) {

    override fun onFire() {
        gameScreen.entityFactory.createCannonBullet(gameScreen.world, angle)
    }
}