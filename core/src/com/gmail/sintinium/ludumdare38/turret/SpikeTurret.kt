package com.gmail.sintinium.ludumdare38.turret

import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.ui.TurretFactory

class SpikeTurret(angle: Float) : BaseTurret(TurretFactory.TurretType.SPIKE, angle) {

    var range = 10f

    override fun onFire() {
        gameScreen.entityFactory.createSpikeBullet(gameScreen.world, angle, range)
    }

}