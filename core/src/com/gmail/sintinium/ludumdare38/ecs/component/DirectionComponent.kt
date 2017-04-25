package com.gmail.sintinium.ludumdare38.ecs.component

import com.artemis.Component

class DirectionComponent : Component() {

    /**
     * Direction on the planet to go (angle)
     */
    var direction = 1f

    var slowDownScale = 1f

}