package eup.mobi.example.adapter

import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import eup.mobi.example.databinding.ItemNewsBinding
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import eup.mobi.example.R
import eup.mobi.example.model.NewsItem
import eup.mobi.example.utils.HandlerThreadCheckSeen


class NewsAdapter(var items: MutableList<NewsItem>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var layoutInflater: LayoutInflater? = null
    private val mHandlerThreadCheckSeen: HandlerThreadCheckSeen<Int>

    init {
        val responseHandler = Handler()
        mHandlerThreadCheckSeen = HandlerThreadCheckSeen(responseHandler)
        mHandlerThreadCheckSeen.setHandlerCheckSeenListener(object : HandlerThreadCheckSeen.HandlerCheckSeenListener<Int>{
            override fun onChecked(target: Int?, isSeen: Boolean) {
                if(target != null)
                items[target].viewed = isSeen
            }
        })
        mHandlerThreadCheckSeen.start()
        mHandlerThreadCheckSeen.looper
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val itemNewsBinding: ItemNewsBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.item_news, parent, false)
        return NewsViewHolder(itemNewsBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemNewsBinding.news = items[position]
        mHandlerThreadCheckSeen.queueCheckSeen(position,items[position])
    }

    inner class NewsViewHolder(val itemNewsBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemNewsBinding.root)
}