package com.gmail.sintinium.ludumdare38.wave

import com.artemis.ComponentMapper
import com.artemis.World
import com.gmail.sintinium.ludumdare38.ecs.component.EnemyComponent
import com.gmail.sintinium.ludumdare38.ecs.system.WaveSystem
import com.gmail.sintinium.ludumdare38.random
import com.gmail.sintinium.ludumdare38.screen.gameScreen

class Wave(val enemies: MutableMap<WaveEnemyType, WaveEnemyData>, val enemyCount: Int, val minSpawnDelay: Int, val maxSpawnDelay: Int) {

    var enemiesDead = 0
    var enemiesSpawned = 0
    var lastSpawnTime = 0L
    var spawnDelay = 0

    lateinit var m: ComponentMapper<EnemyComponent>

    lateinit var world: World

    fun init() {
        world = gameScreen.world
    }

    fun update() {
        if (isWaveFinished()) return
        if (enemiesSpawned >= enemyCount) return
        if (System.currentTimeMillis() - lastSpawnTime > spawnDelay) {
            val nextEnemy = randomEnemyType()
            spawnEnemy(nextEnemy)
            enemiesSpawned++
            nextSpawnDelay()
            lastSpawnTime = System.currentTimeMillis()
        }
    }

    private fun nextSpawnDelay() {
        spawnDelay = random.nextInt(maxSpawnDelay - minSpawnDelay + 1) + minSpawnDelay
    }

    fun isWaveFinished() = enemiesDead >= enemyCount

    fun randomEnemyType(): WaveEnemyType {
        var totalWeight = 0f
        enemies.forEach { type, data ->
            totalWeight += data.spawnChance
        }
        var randomType: WaveEnemyType? = null
        var random = random.nextFloat() * totalWeight
        for ((type, data) in enemies) {
            random -= data.spawnChance
            if (random <= 0f) {
                randomType = type
                break
            }
        }
        return randomType!!
    }

    private fun spawnEnemy(enemyType: WaveEnemyType) {
        val enemyData = enemies[enemyType]
        when (enemyType) {
            WaveEnemyType.YELLOWBLOB -> gameScreen.entityFactory.createBlob(gameScreen.world, enemyData!!.hpScale, enemyData.speedScale, enemyData.coinDropScale)
            WaveEnemyType.PURPLEBLOB -> gameScreen.entityFactory.createPurpleBlob(gameScreen.world, enemyData!!.hpScale, enemyData.speedScale, enemyData.coinDropScale)
            WaveEnemyType.BLORB -> gameScreen.entityFactory.createBlorb(gameScreen.world, enemyData!!.hpScale, enemyData.speedScale, enemyData.coinDropScale)
        }
    }

    data class WaveEnemyData(val spawnChance: Float, val hpScale: Float, val speedScale: Float, val coinDropScale: Float)

}