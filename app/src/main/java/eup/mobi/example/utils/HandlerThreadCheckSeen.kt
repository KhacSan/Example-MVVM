package eup.mobi.example.utils

import android.os.Handler
import android.os.HandlerThread
import android.os.Message

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

import eup.mobi.example.i.MessageCallback
import eup.mobi.example.model.NewsItem


class HandlerThreadCheckSeen<T>(private val mResponseHandler: Handler) :
    HandlerThread(TAG) {
    private var mRequestHandler: Handler? = null
    private val mRequestMap = ConcurrentHashMap<T, NewsItem>()
    private var mHanderListener: HandlerCheckSeenListener<T>? = null

    interface HandlerCheckSeenListener<T> {
        fun onChecked(target: T?, isSeen: Boolean)
    }

    fun setHandlerCheckSeenListener(listener: HandlerCheckSeenListener<T>) {
        mHanderListener = listener
    }


    override fun onLooperPrepared() {
        mRequestHandler = MyHandlerMessage(object : MessageCallback {
            override fun execute(msg: Message) {
                if (msg.what == MESSAGE_CHECK_SEEN) {
                    val target = msg.obj as T
                    handleRequest(target)
                }
            }
        })
    }

    fun queueCheckSeen(target: T, newsItem: NewsItem) {
        mRequestMap[target] = newsItem
        if (mRequestHandler != null)
            mRequestHandler!!.obtainMessage(MESSAGE_CHECK_SEEN, target)
                .sendToTarget()

    }

    fun clearQueue() {
        mRequestHandler!!.removeMessages(MESSAGE_CHECK_SEEN)
    }

    private fun handleRequest(target: T?) {

        // convert furigana
        if (target == null) return

        val newsItem = mRequestMap[target] ?: return


        Handler().postDelayed({
            newsItem.viewed = target is Int && target % 3== 0
        },2000)

        mResponseHandler.post {
            if (mRequestMap[target] == null || mRequestMap[target] != newsItem) {
                return@post
            }
            mRequestMap.remove(target)
            mHanderListener!!.onChecked(target, false)
        }
    }

    companion object {
        private val TAG = "HandlerThreadCheckSeen"

        private val MESSAGE_CHECK_SEEN = 2
    }


}
