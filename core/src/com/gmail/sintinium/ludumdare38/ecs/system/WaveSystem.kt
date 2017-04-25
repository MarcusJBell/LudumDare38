package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.BaseSystem
import com.artemis.annotations.Wire
import com.gmail.sintinium.ludumdare38.random
import com.gmail.sintinium.ludumdare38.wave.Wave
import com.gmail.sintinium.ludumdare38.wave.WaveEnemyType

@Wire
class WaveSystem : BaseSystem() {

    lateinit var starSystem: StarSystem

    var currentWave: Wave? = null
    var waveNumber = 0

    override fun processSystem() {
        if (currentWave == null)
            return

        currentWave!!.update()
        if (currentWave!!.isWaveFinished()) {
            starSystem.closePortal()
            currentWave = null
        }
    }

    fun nextWave() {
        starSystem.openPortal()
        var minDelay = 1000
        var maxDelay = 3000
        if (waveNumber >= 5) {
            minDelay -= 225
            maxDelay -= 500
        }
        if (waveNumber >= 25) {
            minDelay -= 225
            maxDelay -= 750
        }
        if (waveNumber >= 50) {
            minDelay -= 450
            maxDelay -= 1300
        }
        waveNumber++
        currentWave = Wave(generateEnemyStats(), 10 + (10f * (waveNumber / 4f) + random.nextInt(waveNumber).toFloat() * (waveNumber / 25f)).toInt(), minDelay, maxDelay)
        world.inject(currentWave)
        currentWave!!.init()
    }

    fun generateEnemyStats(): MutableMap<WaveEnemyType, Wave.WaveEnemyData> {
        val enemies = mutableMapOf<WaveEnemyType, Wave.WaveEnemyData>()
        enemies.put(WaveEnemyType.YELLOWBLOB, makeEnemyData(.2f + (waveNumber / 10f), .4f + (waveNumber / 20f), 1f + (waveNumber / 25f), 1f + (waveNumber / 15f), 1f + (waveNumber / 200f), 1f + (waveNumber / 125f), 1f + (waveNumber / 7f)))
        enemies.put(WaveEnemyType.PURPLEBLOB, makeEnemyData(.8f + (waveNumber / 7f), 1f + (waveNumber / 15f), 1f + (waveNumber / 40f), 1f + (waveNumber / 25f), 1f + (waveNumber / 125f), 1f + (waveNumber / 75f), 1f  + (waveNumber / 15f)))
        enemies.put(WaveEnemyType.BLORB, makeEnemyData(.05f + (waveNumber / 50f), .1f + (waveNumber / 35f), 1f + (waveNumber / 20f), 1f + (waveNumber / 10f), 1f + (waveNumber / 300f), 1f + (waveNumber / 200f), 1f  + (waveNumber / 5f)))
        return enemies
    }

    fun makeEnemyData(minSpawnChance: Float, maxSpawnChance: Float, minHpScale: Float, maxHpScale: Float, minSpeedScale: Float, maxSpeedScale: Float, coinDropScale: Float): Wave.WaveEnemyData {
        return Wave.WaveEnemyData(floatBetween(minSpawnChance, maxSpawnChance), floatBetween(minHpScale, maxHpScale), floatBetween(minSpeedScale, maxSpeedScale), coinDropScale)
    }

    private fun floatBetween(min: Float, max: Float): Float {
        return random.nextFloat() * (max - min) + min
    }

    fun enemyDestroyed() {
        if (currentWave != null) {
            currentWave!!.enemiesDead++
        }
    }

}