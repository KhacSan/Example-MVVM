package eup.mobi.example.utils

import android.os.Handler
import android.os.Message

import eup.mobi.example.i.MessageCallback


// MARK: custom handler class
class MyHandlerMessage(private val messageCallback: MessageCallback) : Handler() {

    override fun handleMessage(msg: Message) {
        messageCallback.execute(msg)
    }
}