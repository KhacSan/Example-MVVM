package eup.mobi.example.i


import android.os.Message

interface MessageCallback {
    fun execute(message: Message)
}
