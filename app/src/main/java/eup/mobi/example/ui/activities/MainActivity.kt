package eup.mobi.example.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import eup.mobi.example.R
import eup.mobi.example.adapter.NewsVPAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var adapter: NewsVPAdapter
    private var prevMenuItem: MenuItem? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        adapter = NewsVPAdapter(this@MainActivity, supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2

        viewPager.addOnPageChangeListener(this@MainActivity)
        navigationView.setOnNavigationItemSelectedListener(this@MainActivity)

        if (viewModel.mPageEasy == 1)
            viewModel.loadNewsEasy()
    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_easy -> {
                viewPager.setCurrentItem(0, true)
                return true
            }
            R.id.action_difficult -> {
                if (viewModel.mPageDifficult == 1)
                    viewModel.loadNewsDifficult()
                viewPager.setCurrentItem(1, true)
                return true
            }
        }
        return false
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (prevMenuItem != null) {
            prevMenuItem!!.isChecked = false
        } else {
            navigationView.menu.getItem(0).isChecked = false
        }

        navigationView.menu.getItem(position).isChecked = true
        prevMenuItem = navigationView.menu.getItem(position)
    }
}
