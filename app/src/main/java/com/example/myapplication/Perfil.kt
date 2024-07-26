package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater.from
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.databinding.ActivityPerfilBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.ads.AdRequest

class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnMenu.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        binding.btnSegundario.setOnClickListener{
            val intent = Intent(this, PetMenu::class.java)
            startActivity(intent)
        }

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val conpass = bundle?.getString("conpass")
        setup(email ?: "", conpass ?: "")

        val prefs = getSharedPreferences( (getString(R.string.prefs_file)), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("conpass", conpass)
        prefs.apply()

        initLoadAds()
    }

    private fun initLoadAds() {
        val adRequest = com.google.android.gms.ads.AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }

    private fun setup(email: String, conpass: String){
        title = "perfil"
        binding.TextViewEmail.text = email
        binding.TextViewconpass.text = conpass

        binding.btnCerrar.setOnClickListener{
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

        }

    }

}