package com.gmail.sintinium.ludumdare38.ecs.component

import com.artemis.Component
import com.gmail.sintinium.ludumdare38.turret.bullet.BaseBullet

class BulletComponent : Component() {
    lateinit var bullet: BaseBullet
    //Turrets with DoT like the tesla and spikes needs it's damage to be multiplied by DeltaTime or else it does varying damage by FPS
    var damageOverTime = false
}