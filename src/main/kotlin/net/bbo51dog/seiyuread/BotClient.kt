package net.bbo51dog.seiyuread

import net.bbo51dog.seiyuread.audio.AudioService
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag

class BotClient : ListenerAdapter() {

    companion object {
        private const val COMMAND_PREFIX = "sr-"
        private const val COMMAND_JOIN = COMMAND_PREFIX + "join"
    }

    private lateinit var jda: JDA
    private val audioService = AudioService()

    fun run(token: String) {

        val intents = listOf(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.MESSAGE_CONTENT,
        )
        val cacheFlags = listOf(
            CacheFlag.VOICE_STATE,
        )

        jda = JDABuilder.createLight(token, intents)
            .enableCache(cacheFlags)
            .setRawEventsEnabled(true)
            .addEventListeners(this)
            .addEventListeners(Listener(audioService))
            .setStatus(OnlineStatus.ONLINE)
            .setActivity(Activity.playing("せいゆうちゃん"))
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .build()

        jda.awaitReady()

        val slash: SlashCommandData = Commands.slash(COMMAND_JOIN, "せいゆうちゃんがVCにお邪魔します♡")
        val cmdAry = arrayListOf<CommandData>(slash)

        jda.guilds.forEach {
            it.updateCommands().queue()
            it.updateCommands().addCommands(cmdAry).queue()
        }
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            COMMAND_JOIN -> join(event)
        }
    }

    private fun join(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            val voiceChannel = event.member?.voiceState?.channel
            voiceChannel?.let {
                guild.audioManager.openAudioConnection(voiceChannel)
                event.reply("ボイスチャンネルに接続しました").setEphemeral(false).queue()
            } ?: event.reply("ボイスチャンネルがみつかりません").queue()
        }
    }
}