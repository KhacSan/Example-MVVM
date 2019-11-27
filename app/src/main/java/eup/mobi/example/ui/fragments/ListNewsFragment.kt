package eup.mobi.example.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import eup.mobi.example.R
import eup.mobi.example.adapter.NewsAdapter
import eup.mobi.example.model.DataResource
import eup.mobi.example.ui.activities.MainViewModel

class ListNewsFragment : Fragment() {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var textMessage: TextView
    private lateinit var recyclerNews: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var mPosition = 0
    private lateinit var viewModel: MainViewModel
    private var adapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_bar)
        textMessage = view.findViewById(R.id.text_message)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        recyclerNews = view.findViewById(R.id.recycler_news)

        swipeRefresh.setOnRefreshListener {
            if (adapter != null) {
                adapter!!.items.clear()
                adapter!!.notifyDataSetChanged()
                adapter = null
            }
            loadCurrentPageNews()
        }

        swipeRefresh.setColorSchemeResources(
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        mPosition = arguments!!.getInt("POSITION")

        loadNews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    private fun loadNews() {
        val liveData = when (mPosition) {
            0 -> viewModel.mNewsEasy
            else -> viewModel.mNewsDifficult
        }

        liveData.observe(this, Observer {
            if (it.status == DataResource.Status.LOADING) {
                if (mPosition == 0 && viewModel.mPageEasy == 1)
                    progressBar.visibility = View.VISIBLE
                else if (mPosition == 1 && viewModel.mPageDifficult == 1)
                    progressBar.visibility = View.VISIBLE
            } else if (it.status == DataResource.Status.SUCCESS && it.data != null) {
                if (progressBar.visibility != View.GONE)
                    progressBar.visibility = View.GONE
                if (adapter == null) {
                    when {
                        it.data.isNotEmpty() -> {
                            adapter = NewsAdapter(
                                it.data
                            )
                            recyclerNews.adapter = adapter
//                            if (it.data.size >= LIMIT)
//                                recyclerNews.addOnScrollListener(onScrollList)
                            hideMessage()
                        }
                        else -> showMessage(getString(R.string.empty_list_post))
                    }
                } else {
//                    adapter!!.setLoadingMore(false, it.data)
//                    if (it.data.size < LIMIT && adapter!!.itemCount >= LIMIT)
//                        recyclerNews.removeOnScrollListener(onScrollList)
                }

                if (swipeRefresh.isRefreshing)
                    swipeRefresh.isRefreshing = false
            } else {
                if (mPosition == 0 && viewModel.mPageEasy == 1 || mPosition == 1 && viewModel.mPageDifficult == 1)
                    showMessage(if (it.message.isNullOrEmpty()) getString(R.string.something_went_wrong) else it.message)
//                else
//                    adapter.setLoadingMore(false, null)

                if (progressBar.visibility != View.GONE)
                    progressBar.visibility = View.GONE

                if (swipeRefresh.isRefreshing)
                    swipeRefresh.isRefreshing = false
            }
        })
    }

    private fun loadCurrentPageNews() {
        when (mPosition) {
            0 -> viewModel.loadNewsEasy()
            else -> viewModel.loadNewsDifficult()
        }
    }

    private val onScrollList: RecyclerView.OnScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

//                val layoutManager = recyclerNews.layoutManager
//                if (layoutManager is LinearLayoutManager) {
//                    val totalItemCount = layoutManager.getItemCount()
//                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
//                    if (adapter != null && !adapter!!.isLoadingMore && totalItemCount <= (lastVisibleItem + 3)) {
//                        recyclerNews.post {
//                            adapter!!.setLoadingMore(true)
//                            loadCurrentPageNews()
//                        }
//                    }
//                }
            }
        }
    }

    private fun showMessage(message: String) {
        textMessage.text = message
        if (textMessage.visibility != View.VISIBLE)
            textMessage.visibility = View.VISIBLE
        if (recyclerNews.visibility != View.GONE)
            recyclerNews.visibility = View.GONE
    }

    private fun hideMessage() {
        if (textMessage.visibility != View.GONE)
            textMessage.visibility = View.GONE
        if (recyclerNews.visibility != View.VISIBLE)
            recyclerNews.visibility = View.VISIBLE
    }

//    private val itemNewCallback: ItemNewsCallback by lazy {
//        object : ItemNewsCallback {
//            override fun onItemClick(id: String) {
//                if (id.isEmpty())
//                    return
//                val intent = Intent(context, NewsActivity::class.java)
//                intent.putExtra("ID", id)
//                intent.putExtra("IS_EASY", mPosition == 0)
//                startActivity(intent)
//            }
//
//            override fun onItemLongClick(id: String) {
//                if (id.isNotEmpty() && !isDetached) {
//                    val newsActionBSDF = NewsActionBSDF.newInstance(id, isFavorite)
//                    newsActionBSDF.show(childFragmentManager, newsActionBSDF.tag)
//                }
//            }
//        }
//    }

    companion object {
        fun newInstance(position: Int): ListNewsFragment {
            val args = Bundle()
            val fragment = ListNewsFragment()
            args.putInt("POSITION", position)
            fragment.arguments = args
            return fragment
        }
    }
}
