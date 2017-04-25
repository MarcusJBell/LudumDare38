package com.gmail.sintinium.ludumdare38.ecs.system

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx

class SoundSystem : BaseSystem() {

    companion object {
        const val MAINSONG = "maintrack.wav"
        const val EXPLOSION = "explosion.wav"
    }

    var music = Gdx.audio.newMusic(Gdx.files.internal("music/$MAINSONG")).also { it.isLooping = true; it.volume = .8f }
    var sound = Gdx.audio.newSound(Gdx.files.internal("music/$EXPLOSION"))

    override fun processSystem() {
        if (!music.isPlaying) {
            music.play()
        }
    }

    fun playExplosion() {
        sound.play(0.3f)
    }

    fun coreExplosion() {
        sound.play(0.35f, 0.5f, 0f)
    }

    fun disposeMusic() {
//        music.dispose()
        sound.dispose()
    }

}