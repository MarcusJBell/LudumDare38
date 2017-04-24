package com.gmail.sintinium.ludumdare38.turret

import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.ui.TurretFactory

class TeslaTurret(angle: Float) : BaseTurret(TurretFactory.TurretType.TESLA, angle) {

    override fun onFire() {
        gameScreen.entityFactory.createTeslaBullet(gameScreen.world, angle)
    }

}