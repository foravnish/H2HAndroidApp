package com.h2h.medical.activities

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.asksira.loopingviewpager.LoopingViewPager
import com.google.android.material.tabs.TabLayout
import com.h2h.medical.R
import com.h2h.medical.adapters.AdapterLoopingViewPager
import com.h2h.medical.fragments.HomeFragment
import com.h2h.medical.fragments.MyAccountFragment
import com.h2h.medical.helper.PermissionHelper

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class HomeActivity : BaseActivity() {

    lateinit var mTabLayout: TabLayout
    lateinit var mViewPager: ViewPager

    private lateinit var mLoopingViewPager: LoopingViewPager
    private lateinit var mAdapterLoopingViewPager: AdapterLoopingViewPager
    private var loopingList = ArrayList<String>()

    private lateinit var mPermissionHelper: PermissionHelper
    private lateinit var permissions: Array<String>


    companion object {
        fun open(myActivity: Activity) {
            val intent = Intent(myActivity, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        initViewPager()
        //setLoopingViewPager()
    }


    private fun setLoopingViewPager() {

        loopingList.add("Straightforward Focused Charting. So you'll have time for the people in front of you.")
        loopingList.add("Easiest way to prove what you did is to have it written")
        loopingList.add("The details in what you write matter")
        loopingList.add("Your documentation tells others what happened during the day. It protects you.")

        mAdapterLoopingViewPager = AdapterLoopingViewPager(this, loopingList, true)
        mLoopingViewPager.adapter = mAdapterLoopingViewPager
        mLoopingViewPager.resumeAutoScroll()

    }


    private fun initViews() {

        mTabLayout = findViewById(R.id.tabLayout)
        mViewPager = findViewById(R.id.viewPager)
//        mLoopingViewPager = findViewById(R.id.loopingViewPager)

        mPermissionHelper = PermissionHelper(this, this)
        permissions = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        mPermissionHelper.requestPermission(
            permissions,
            "Storage permission required for uploading profile picture"
        )

    }

    private fun initViewPager() {
        val tabNames = arrayOf("Home", "My Account")
        val tabIcons =
            arrayOf(
                R.drawable.ic_home,
//                R.drawable.ic_my_reports,
                R.drawable.ic_my_account
            )

        mTabLayout.addTab(mTabLayout.newTab())
//        mTabLayout.addTab(mTabLayout.newTab())
        mTabLayout.addTab(mTabLayout.newTab())

        for (i in 0 until 2) {
            val tab = LayoutInflater.from(this).inflate(
                R.layout.row_main_tab_layout,
                null
            ) as LinearLayout

            val tabIcon = tab.findViewById(R.id.icon) as ImageView
            val tabLabel = tab.findViewById(R.id.label) as TextView

            if (i == 0) {
                tabLabel.setTextColor(resources.getColor(R.color.selectedTabColor))
                tabIcon.setColorFilter(
                    resources.getColor(R.color.selectedTabColor),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                tabLabel.setTextColor(resources.getColor(R.color.white))
                tabIcon.setColorFilter(
                    resources.getColor(R.color.white),
                    PorterDuff.Mode.SRC_IN
                )
            }

            tabIcon.setImageResource(tabIcons[i])
            tabLabel.text = tabNames[i]
            mTabLayout.getTabAt(i)!!.customView = tab
        }

        mViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mTabLayout))
        mViewPager.offscreenPageLimit = mTabLayout.tabCount
        mViewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment.newInstance()
//                    1 -> MyReportsFragment.newInstance()
                    1 -> MyAccountFragment.newInstance()
                    else -> HomeFragment.newInstance()
                }
            }

            override fun getCount(): Int {
                return 2
            }
        }

        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

                mViewPager.currentItem = tab.position
//                refreshTab(tab.position)

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

                val view = tab.customView
                val tabIcon = view?.findViewById(R.id.icon) as ImageView
                val tabLabel = view.findViewById(R.id.label) as TextView
                tabLabel.setTextColor(resources.getColor(R.color.white))
                tabIcon.setColorFilter(
                    resources.getColor(R.color.white),
                    PorterDuff.Mode.SRC_IN
                )
            }

            override fun onTabSelected(tab: TabLayout.Tab) {

                mViewPager.currentItem = tab.position
//                    refreshTab(tab.position)

                val view = tab.customView
                val tabIcon = view?.findViewById(R.id.icon) as ImageView
                val tabLabel = view.findViewById(R.id.label) as TextView
                tabLabel.setTextColor(resources.getColor(R.color.selectedTabColor))
                tabIcon.setColorFilter(
                    resources.getColor(R.color.selectedTabColor),
                    PorterDuff.Mode.SRC_IN
                )
            }
        })
    }
}
