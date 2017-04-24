package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.gmail.sintinium.ludumdare38.ecs.component.CoinDropComponent
import com.gmail.sintinium.ludumdare38.screen.gameScreen

@Wire
class CoinDropSystem : BaseEntitySystem(Aspect.all(CoinDropComponent::class.java)) {

    lateinit var mCoin: ComponentMapper<CoinDropComponent>

    override fun processSystem() {
    }

    override fun removed(entityId: Int) {
        val cCoin = mCoin[entityId]
        if (!cCoin.shouldDrop) return
        gameScreen.coinBankSystem.addCoins(cCoin.dropAmount)
    }
}