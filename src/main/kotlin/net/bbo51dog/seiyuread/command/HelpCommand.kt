package net.bbo51dog.seiyuread.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class HelpCommand(private val executor: CommandExecutor) : Command() {

    override val name: String = COMMAND_PREFIX + "help"

    override val description: String = "わたしの使い方をお教えします！"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        var str = "```"
        executor.commandMap.forEach { (_, command) ->
            str += "\n${command.name} : ${command.description}"
        }
        str += "```"
        event.reply(str)
    }
}