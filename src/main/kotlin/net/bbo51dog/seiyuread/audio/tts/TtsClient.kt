package net.bbo51dog.seiyuread.audio.tts

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import java.io.File

class TtsClient {

    companion object {
        private const val VOICEVOX_URL = "http://localhost:50021"
        private const val SPEAKER_ID = 14

        fun saveVoiceToCache(text: String) : File {
            val (_, response, _) = Fuel.post("$VOICEVOX_URL/audio_query?text=$text&speaker=$SPEAKER_ID")
                .header("accept", "application/json")
                .responseString()
            val (_, response2, _) = Fuel.post("$VOICEVOX_URL/synthesis?speaker=$SPEAKER_ID")
                .header("accept", "audio/wav")
                .header("Content-Type", "application/json")
                .jsonBody(response.body().asString("application/json"))
                .response()
            val file = kotlin.io.path.createTempFile(suffix = ".wav").toFile()
            response2.body().writeTo(file.outputStream())
            return file
        }
    }
}