package net.bbo51dog.seiyuread

const val TOKEN_ENV = "SEIYU_TOKEN"
fun main(){
    BotClient().run(System.getenv(TOKEN_ENV))
}