package com.gmail.sintinium.ludumdare38.ecs.factory

import com.artemis.ComponentMapper
import com.artemis.World
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.gmail.sintinium.ludumdare38.Game
import com.gmail.sintinium.ludumdare38.ecs.component.*
import com.gmail.sintinium.ludumdare38.game
import com.gmail.sintinium.ludumdare38.screen.gameScreen
import com.gmail.sintinium.ludumdare38.turret.*
import com.gmail.sintinium.ludumdare38.turret.bullet.*
import com.gmail.sintinium.ludumdare38.ui.TurretFactory
import java.util.*

class EntityFactory {

    lateinit var mPosition: ComponentMapper<PositionComponent>
    lateinit var mTexture: ComponentMapper<TextureComponent>
    lateinit var mDirection: ComponentMapper<DirectionComponent>
    lateinit var mHealth: ComponentMapper<HealthComponent>
    lateinit var mTurret: ComponentMapper<TurretComponent>
    lateinit var mBullet: ComponentMapper<BulletComponent>
    lateinit var mCoin: ComponentMapper<CoinDropComponent>
    lateinit var mEnemy: ComponentMapper<EnemyComponent>

    fun createCore(world: World): Int {
        val e = world.create()
        mPosition.create(e).also {
            it.height = -16f
            it.zOrder = 5f
            it.angle += 2f
        }
        mTexture.create(e).also {
            it.texture = game.textureAtlas.findRegion(Game.CORE)
            it.originX = it.texture!!.regionWidth / 2f
            it.xOffset = -it.texture!!.regionWidth / 2f
        }
        return e
    }

    fun createBlob(world: World, hpScale:Float, speedScale: Float, coinDropScale: Float): Int {
        val e = world.create()
        val animation = Animation<TextureRegion>(1 / 5f, game.textureAtlas.findRegions(Game.BLOB), Animation.PlayMode.LOOP)
        createEnemy(e, animation, 10f * speedScale, 175f * hpScale, (10f * coinDropScale).toInt())
        return e
    }

    fun createPurpleBlob(world: World, hpScale:Float, speedScale: Float, coinDropScale: Float): Int {
        val e = world.create()
        val animation = Animation<TextureRegion>(1 / 5f, game.textureAtlas.findRegions(Game.PURPLE_BLOB), Animation.PlayMode.LOOP)
        createEnemy(e, animation, 13f * speedScale, 100f * hpScale, (5f * coinDropScale).toInt())
        return e
    }

    fun createBlorb(world: World, hpScale:Float, speedScale: Float, coinDropScale: Float): Int {
        val e = world.create()
        val animation = Animation<TextureRegion>(1 / 5f, game.textureAtlas.findRegions(Game.BLORB), Animation.PlayMode.LOOP)
        createEnemy(e, animation, 5f * speedScale, 300f * hpScale, (25f * coinDropScale).toInt())
        return e
    }

    private fun createEnemy(entityId: Int, animation: Animation<TextureRegion>, speed: Float, health: Float, coinDropAmount: Int) {
        val random = Random() //todo remove this and replace it with proper system
        mPosition.create(entityId).also {
            it.angle = 180f
            it.height = -2f
        }
        mTexture.create(entityId).also {
            it.animation = animation
            val texture = it.animation!!.keyFrames!!.first()!!
            it.originX = texture.regionWidth / 2f
            it.xOffset = -texture.regionWidth / 2f
        }
        mDirection.create(entityId).also {
            if (random.nextInt(2) == 0) {
                it.direction = speed
            } else {
                it.direction = -speed
            }
        }
        mHealth.create(entityId).also {
            it.currentHealth = health
        }
        mCoin.create(entityId).also {
            it.dropAmount = coinDropAmount
            it.shouldDrop = true
        }
        mEnemy.create(entityId)
    }

    fun createTesla(world: World, angle: Float): Int {
        val e = world.create()
        createTurret(e, angle, game.textureAtlas.findRegion(TurretFactory.TurretType.TESLA.textureName), TeslaTurret(angle).also { it.shootDelay = 10f })
        return e
    }

    fun createTeslaBullet(world: World, angle: Float): Int {
        val e = world.create()
        createBullet(world, e, angle, TeslaBullet(world, e, angle, 200f, getDir(angle)))
        return e
    }

    fun createQuake(world: World, angle: Float): Int {
        val e = world.create()
        createTurret(e, angle, Animation<TextureRegion>(1/2f, game.textureAtlas.findRegions(TurretFactory.TurretType.QUAKE.textureName)), QuakeTurret(angle).also { it.shootDelay = 15f })
        return e
    }

    fun createQuakeBullet(world: World, angle: Float): Int {
        val e = world.create()
        createBullet(world, e, angle, QuakeBullet(world, e, angle, 400f, getDir(angle)))
        return e
    }

    fun createGuardian(world: World, angle: Float): Int {
        val e = world.create()
        createTurret(e, angle, game.textureAtlas.findRegion(TurretFactory.TurretType.GUARDIAN.textureName), GuardianTurret(angle).also { it.shootDelay = 30f; it.waitingTime = it.shootDelay }, GuardianTurret.RANGE, false, true)
        mPosition[e].height = GuardianTurret.HEIGHT
        return e
    }

    fun createGuardianBullet(world: World, angle: Float, targetPosition: Vector2, height: Float): Int {
        val e = world.create()
        createBullet(world, e, angle, GuardianBullet(world, e, angle, targetPosition, height))
        return e
    }

    fun createSpikeTurret(world: World, angle: Float): Int {
        val e = world.create()
        createTurret(e, angle, game.textureAtlas.findRegion(TurretFactory.TurretType.SPIKE.textureName), SpikeTurret(angle).also { it.shootDelay = 2.5f }, 5f, false, true)
        return e
    }

    fun createSpikeBullet(world: World, angle: Float, range: Float): Int {
        val e = world.create()
        createBullet(world, e, angle, SpikeBullet(world, e, angle, range))
        return e
    }

    fun createCannonTurret(world: World, angle: Float): Int {
        val e = world.create()
        createTurret(e, angle, game.textureAtlas.findRegion(TurretFactory.TurretType.CANNON.textureName), CannonTurret(angle).also { it.shootDelay = 2f })
        return e
    }

    fun createCannonBullet(world: World, angle: Float): Int {
        val e = world.create()
        createBullet(world, e, angle, CannonBullet(world, e, angle, getDir(angle)))
        return e
    }

    private fun createTurret(entityId: Int, angle: Float, textureRegion: TextureRegion, turret: BaseTurret, range: Float = 0f, sided: Boolean = true, usesRange: Boolean = false) {
        mPosition.create(entityId).also {
            it.angle = angle
            it.zOrder = -1f
        }
        mTexture.create(entityId).also {
            it.texture = textureRegion
            it.originX = it.texture!!.regionWidth / 2f
            it.xOffset = -it.texture!!.regionWidth / 2f
        }
        mDirection.create(entityId).also {
            it.direction = getDir(angle).toFloat()
        }
        mTurret.create(entityId).also {
            it.turret = turret
            it.range = range
            it.sided = sided
            it.usesRange = usesRange
        }
    }

    private fun createTurret(entityId: Int, angle: Float, animation: Animation<TextureRegion>, turret: BaseTurret, range: Float = 0f, sided: Boolean = true, usesRange: Boolean = false) {
        mPosition.create(entityId).also {
            it.angle = angle
            it.zOrder = -1f
        }
        mTexture.create(entityId).also {
            it.animation = animation
            it.originX = it.animation!!.keyFrames.first().regionWidth / 2f
            it.xOffset = -it.animation!!.keyFrames.first().regionWidth / 2f
        }
        mDirection.create(entityId).also {
            it.direction = getDir(angle).toFloat()
        }
        mTurret.create(entityId).also {
            it.turret = turret
            it.range = range
            it.sided = sided
            it.usesRange = usesRange
        }
    }

    private fun createBullet(world: World, entityId: Int, angle: Float, bullet: BaseBullet) {
        mBullet.create(entityId).also {
            it.bullet = bullet
        }
    }

    private fun getDir(angle: Float) : Int {
        return if (angle < 180) 1 else -1
    }

}