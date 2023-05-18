package com.example.bottomnavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navigationBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        navigationBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(navigationBinding.root)
        insertFragment(HomeFragment())

        navigationBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> insertFragment(HomeFragment())
                //go to a normal kotlin class file
                R.id.post -> insertFragment(PostFragment())
                R.id.build -> insertFragment(BuildFragment())
                R.id.profile -> insertFragment(ProfileFragment())

                else -> {

                }
            }
            true

        }


    }

    private fun insertFragment(fragment: Fragment) {
        val fragmentMan = supportFragmentManager
        val fragmentTrans = fragmentMan.beginTransaction()
        fragmentTrans.replace(R.id.frame_layout, fragment)
        fragmentTrans.commit()
    }

}