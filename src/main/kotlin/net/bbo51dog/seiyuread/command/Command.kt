package net.bbo51dog.seiyuread.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

interface Command {

    companion object {
        const val COMMAND_PREFIX = "sr-"
    }

    val name: String

    val description: String

    val commandData: SlashCommandData
        get() =  Commands.slash(name, description)

    fun execute(event: SlashCommandInteractionEvent)
}