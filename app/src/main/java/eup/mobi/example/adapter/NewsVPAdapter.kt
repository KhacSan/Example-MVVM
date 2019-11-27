package eup.mobi.example.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import eup.mobi.example.R
import eup.mobi.example.ui.fragments.ListNewsFragment


class NewsVPAdapter(private val context: Context,fm: FragmentManager) : FragmentStatePagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var easyNewsFragment: ListNewsFragment? = null
    private var diffNewsFragment: ListNewsFragment? = null

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                if(easyNewsFragment == null)
                    easyNewsFragment = ListNewsFragment.newInstance(0)
                easyNewsFragment!!
            }
            1 -> {
                if(diffNewsFragment == null)
                    diffNewsFragment = ListNewsFragment.newInstance(1)
                diffNewsFragment!!
            }
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return context.resources.getString(R.string.easy)
            1 -> return context.resources.getString(R.string.diff)
        }
        return super.getPageTitle(position)
    }
}