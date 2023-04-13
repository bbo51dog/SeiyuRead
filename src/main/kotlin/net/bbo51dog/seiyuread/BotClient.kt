package net.bbo51dog.seiyuread

import net.bbo51dog.seiyuread.audio.AudioService
import net.bbo51dog.seiyuread.command.CommandExecutor
import net.bbo51dog.seiyuread.command.HelpCommand
import net.bbo51dog.seiyuread.command.JoinCommand
import net.bbo51dog.seiyuread.listener.CommandListener
import net.bbo51dog.seiyuread.listener.CommonListener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag

class BotClient {

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

        val commandExecutor = CommandExecutor()

        jda = JDABuilder.createLight(token, intents)
            .enableCache(cacheFlags)
            .setRawEventsEnabled(true)
            .addEventListeners(
                CommonListener(audioService),
                CommandListener(commandExecutor),
            )
            .setStatus(OnlineStatus.ONLINE)
            .setActivity(Activity.playing("せいゆうちゃん"))
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .build()
        jda.awaitReady()

        commandExecutor.registerCommands(
            jda,
            JoinCommand(),
            HelpCommand(commandExecutor),
        )
    }
}