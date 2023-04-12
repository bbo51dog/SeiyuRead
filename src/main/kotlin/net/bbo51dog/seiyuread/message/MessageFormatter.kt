package net.bbo51dog.seiyuread.message

import net.dv8tion.jda.api.entities.Message

object MessageFormatter {

    private const val MAX_LENGTH = 50

    private val urlRegex = Regex("""http(s)?://([\w-]+.)+[\w-]+(/[\w-./?%&=]*)?""")

    fun format(message: Message): String {
        var str = message.contentDisplay
        str.replace(urlRegex, "URL")
        if (str.length > MAX_LENGTH) {
            str.dropLast(str.length - MAX_LENGTH)
            str += "省略"
        }
        return str
    }
}