package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Adapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBindings

class Home : AppCompatActivity(){
    private lateinit var currentFragment: Fragment
    private lateinit var binding: ActivityHomeBinding
    @RequiresApi(VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

            super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().replace(R.id.nav_container,CasaFragment()).commit()
        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

//        val mToolbar : Toolbar? = findViewById(R.id.toolbar)
//        setSupportActionBar(mToolbar)
//        supportActionBar?.title = "estas en el home.kt"
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)

//        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
//        val adapter =  Adaptador()
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter


    }



    val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.casaFragment -> {
                currentFragment =  CasaFragment()
                true

            }
            R.id.dashboardFragment -> {
                currentFragment = DashboardFragment()
                true
            }
            R.id.listFragment -> {
                currentFragment = ListFragment()
                true
            }

            R.id.petFragment -> {
                currentFragment = PetFragment()
                true
            }

            R.id.perfil -> {
                    val intent = Intent(this,  Perfil::class.java)
                    startActivity(intent)

            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.nav_container,currentFragment).commit()
        true
    }


}