package com.gmail.sintinium.ludumdare38.ecs.component

import com.artemis.Component
import com.artemis.annotations.DelayedComponentRemoval

@DelayedComponentRemoval
class CoinDropComponent : Component() {

    //Should be set to false if the player does kill the enemy. Ex. hitting the core kills the enemy but shouldn't drop free coins
    var shouldDrop = true
    var dropAmount = 5

}