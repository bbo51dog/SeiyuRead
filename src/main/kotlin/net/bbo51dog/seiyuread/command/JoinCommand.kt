package net.bbo51dog.seiyuread.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class JoinCommand : Command() {

    override val name: String = COMMAND_PREFIX + "join"
    override val description: String = "せいゆうちゃんがVCにお邪魔します♡"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            val voiceChannel = event.member?.voiceState?.channel
            voiceChannel?.let {
                guild.audioManager.openAudioConnection(voiceChannel)
                event.reply("ボイスチャンネルに接続しました").setEphemeral(false).queue()
            } ?: event.reply("ボイスチャンネルがみつかりません").queue()
        }
    }
}