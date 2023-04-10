package net.bbo51dog.seiyuread

import net.bbo51dog.seiyuread.audio.AudioService
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener

class Listener(private val audioService: AudioService) : EventListener {

    override fun onEvent(event: GenericEvent) {
        when(event) {
            is MessageReceivedEvent -> {
                if (event.guild.audioManager.isConnected) {
                    audioService.speak(event.message)
                }
            }
        }
    }
}