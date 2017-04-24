package com.gmail.sintinium.ludumdare38.ui

import com.artemis.World
import com.gmail.sintinium.ludumdare38.ecs.factory.EntityFactory

class TurretFactory(val world: World, val entityFactory: EntityFactory) {

    fun createTurretFromType(turretType: TurretType, angle: Float): Int {
        when (turretType) {
            TurretType.TESLA -> return entityFactory.createTesla(world, angle)
            TurretType.GUARDIAN -> return entityFactory.createGuardian(world, angle)
            TurretType.SPIKE -> return entityFactory.createSpikeTurret(world, angle)
            TurretType.CANNON -> return entityFactory.createCannonTurret(world, angle)
        }
    }

    enum class TurretType(val turretName: String, val textureName: String, var turretPrice: Int, var description: String) {
        TESLA("Tesla", "tesla", 50, teslaInfo),
        GUARDIAN("Guardian", "eyeturret", 500, guardianInfo),
        SPIKE("Spike", "Spiketurret", 25, spikeInfo),
        CANNON("Cannon", "Cannonturret", 10, cannonInfo)
    }

    private companion object {
        const val teslaInfo = "Shoots a jolt of electricity towards the portal doing damage over time and spanning a large area"
        const val guardianInfo = "Hovers over the core protecting it from enemies with a slow fire rate and short range but huge damage"
        const val spikeInfo = "Shoots spikes up when enemies are near by dealing large damage in a short radius"
        const val cannonInfo = "Long range cannon that deals decent damage and with moderate fire rate"
    }
}