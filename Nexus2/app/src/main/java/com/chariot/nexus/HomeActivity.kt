package com.chariot.nexus

import ProfileFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chariot.nexus.Fragments.AddFragment
import com.chariot.nexus.Fragments.HomeFragment
import com.chariot.nexus.Fragments.ReelsFragment
import com.chariot.nexus.Fragments.SearchFragment
import com.chariot.nexus.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_search -> replaceFragment(SearchFragment())
                R.id.navigation_add -> replaceFragment(AddFragment())
                R.id.navigation_reels -> replaceFragment(ReelsFragment())
                R.id.navigation_profile -> replaceFragment(ProfileFragment())
                else ->{



                }

            }

            true

        }


    }

    public fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}