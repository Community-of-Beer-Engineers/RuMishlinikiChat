package com.example.chat_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chat_mobile.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Replace the initial fragment here
        replaceFragment(forumFragment())

        binding.bottomPanelNavBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.forum -> replaceFragment(forumFragment())
                R.id.chats -> replaceFragment(ChatFragment())
                R.id.other -> replaceFragment(OtherFragment())
                R.id.setting -> replaceFragment(SettingFragment())

                else -> {

                }
            }
            true
        }
    }

    // Replace the current fragment with a new fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}
