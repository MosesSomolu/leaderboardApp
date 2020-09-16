package com.example.leaderboardapp.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.leaderboardapp.R
import com.example.leaderboardapp.fragments.LearnersFragment
import com.example.leaderboardapp.fragments.SkillsFragment
import com.example.leaderboardapp.ui.adapters.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewRefs()
        setUpAdapter()

        //Initiate LeadersFragment in MainActivity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.view_pager,
                LearnersFragment.newInstance(), "LearnersFragment").commit()
        }

        //Initiate SkillsFragment in MainActivity
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.view_pager,
                    SkillsFragment.newInstance(), "SkillFragment"
                )
                .commit()
        }

        submitButton.setOnClickListener {
            val intent = Intent(this, SubmitActivity::class.java)
            startActivity(intent)
        }

    }

    // FUNCTIONS
    private fun viewRefs(){
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tabs)
        tabLayout?.setupWithViewPager(viewPager)
    }


    //Function to link viewPager Adapter with MainActivity and initialize click listeners for viewPager and TabLayout
    private fun setUpAdapter() {
        val pagerAdapter = PagerAdapter(supportFragmentManager)
        view_pager.adapter = pagerAdapter

        initializeVPChangeListener()
        initializeTabChangeListener()
    }

    //Function to initialize change listener for ViewPager
    private fun initializeVPChangeListener() {
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    //Function to initialize Tab change listener when to tab is tapped by user, not swiped.
    private fun initializeTabChangeListener() {
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }
}