package net.bbo51dog.seiyuread.listener

import net.bbo51dog.seiyuread.command.CommandExecutor
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandListener(
    private val commandExecutor: CommandExecutor
) : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        commandExecutor.handleCommand(event)
    }
}