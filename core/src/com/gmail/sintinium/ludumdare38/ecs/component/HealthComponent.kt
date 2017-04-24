package com.gmail.sintinium.ludumdare38.ecs.component

import com.artemis.Component

class HealthComponent : Component() {
//    var maxHealth = 10f
    var currentHealth = 10f
    var currentDamageTaken = 0f

    /**
     * Used to determine how high above the enemy to draw the hp bar
     */
    var yOffset = 0f

    fun damage(amount: Float) {
        currentDamageTaken += amount
    }
}