package binar.finalproject.binair.admin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavListener()
    }

    private fun setBottomNavListener() {
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navBuyTicket -> {
                    Navigation.findNavController(this, R.id.fragmentContainer)
                        .navigate(R.id.action_global_homeFragment2)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navTicketlist -> {
                    Navigation.findNavController(this, R.id.fragmentContainer)
                        .navigate(R.id.action_global_ticketListFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navAddNotif -> {
                    Navigation.findNavController(this, R.id.fragmentContainer)
                        .navigate(R.id.action_global_addNotificationFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navProfile -> {
                    Navigation.findNavController(this, R.id.fragmentContainer)
                        .navigate(R.id.action_global_profileFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}