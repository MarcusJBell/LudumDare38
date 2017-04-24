package com.gmail.sintinium.ludumdare38.ecs.component

import com.artemis.Component
import com.gmail.sintinium.ludumdare38.turret.BaseTurret

class TurretComponent : Component() {

    lateinit var turret: BaseTurret
    var usesRange = false
    var range = 0f
    // Should the system check if there are enemies on both sides or only the way the turret is facing. (true means 1 side)
    var sided = true

}