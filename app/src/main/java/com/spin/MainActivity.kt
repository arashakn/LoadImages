package com.spin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spin.adapters.ViewPagerAdapter
import com.spin.fragments.ImagesFavListFragment
import com.spin.fragments.ImagesListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if(savedInstanceState == null){
//            supportFragmentManager.beginTransaction().add(R.id.flContainer, ImagesListFragment()).commitAllowingStateLoss()
//        }


        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ImagesListFragment(), "Images")
        adapter.addFragment(ImagesFavListFragment(), "Fav Images")


        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }
}
