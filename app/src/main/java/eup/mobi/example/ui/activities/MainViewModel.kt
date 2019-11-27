package eup.mobi.example.ui.activities

import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eup.mobi.example.model.DataResource
import eup.mobi.example.model.NewsItem
import eup.mobi.example.utils.GetNewsHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel(), LifecycleObserver {
    var mPageEasy = 1
    var mPageDifficult = 1

    val mNewsEasy: MutableLiveData<DataResource<MutableList<NewsItem>>> by lazy {
        MutableLiveData<DataResource<MutableList<NewsItem>>>()
    }
     val mNewsDifficult: MutableLiveData<DataResource<MutableList<NewsItem>>> by lazy {
        MutableLiveData<DataResource<MutableList<NewsItem>>>()
    }

    private val mDisposable = CompositeDisposable()


    fun loadNewsEasy() {
        mNewsEasy.postValue(DataResource.loading("Loading news easy for page: $mPageEasy..."))

        mDisposable.add(
            GetNewsHelper.getMaziiApi().getNewsEasy(mPageEasy, 10)
            .map {
                val news = ArrayList<NewsItem>()
                if (it.results != null) {
                    val ids = arrayOfNulls<String>(it.results!!.size)
                    var i = 0
                    for (result in it.results!!.iterator()) {
                        val new = NewsItem()
                        new.title = result.value?.title ?: ""
                        new.date = result.key
                        new.id = result.id
                        new.image = result.value?.image ?: ""
//                        if (!new.id.isNullOrEmpty())
//                            new.isSeen = MyWordDatabase.getInstance(context).isSeen(new.id!!)
                        news.add(new)
                        ids[i] = result.id
                        i += 1
                    }
//                    DownloadNewsWorker.startScheduleDownloadNews(context, ids, true)
                }
                news
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mNewsEasy.postValue(DataResource.success(it))
                mPageEasy += 1
            }, {
                mNewsEasy.postValue(DataResource.error(it.message ?: ""))
                it.printStackTrace()
            })
        )

    }


    fun loadNewsDifficult() {

        mDisposable.add(GetNewsHelper.getMaziiApi().getNewsDifficult(mPageDifficult, 10)
            .map {
                val news = ArrayList<NewsItem>()
                if (it.results != null) {
                    val sdf = SimpleDateFormat("MM月dd日HH時mm分", Locale.getDefault())
                    val ids = arrayOfNulls<String>(it.results!!.size)
                    var i = 0
                    for (result in it.results!!.iterator()) {
                        val new = NewsItem()
                        new.title = result.value?.title ?: ""
                        new.date = sdf.format(
                            Date(
                                result.key
                                    ?: System.currentTimeMillis()
                            )
                        )
                        new.id = result.id
//                        if (!new.id.isNullOrEmpty())
//                            new.isSeen = MyWordDatabase.getInstance(context).isSeen(new.id!!)
                        new.image = result.value?.image ?: ""
                        news.add(new)
                        ids[i] = result.id
                        i += 1
                    }
//                    DownloadNewsWorker.startScheduleDownloadNews(context, ids, false)
                }
                news
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mNewsDifficult.postValue(DataResource.success(it))
                mPageDifficult += 1
            }, {
                mNewsDifficult.postValue(DataResource.error(it.message ?: ""))
                it.printStackTrace()
            })
        )


    }


    override fun onCleared() {
        mDisposable.dispose()
        super.onCleared()
    }
}