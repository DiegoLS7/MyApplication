package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.text.set
import com.example.myapplication.databinding.ActivityCrearUsuarioBinding
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session()

        firebaseAuth = FirebaseAuth.getInstance()
        binding.txtNoCuenta.setOnClickListener{
            val intent = Intent(this,  CrearUsuario::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener{
            val email = binding.txtEmail.text.toString()
            val conpass = binding.txtPass.text.toString()
            if (email.isNotEmpty() && conpass.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email, conpass).addOnCompleteListener{
                        if (it.isSuccessful){

                            val intent = Intent(this, Perfil::class.java)
                            startActivity(intent)

                            val chanelID = "chat"
                            val channelName = "chat"

                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_0_1){
                                val importancia = NotificationManager.IMPORTANCE_HIGH
                                val canal = NotificationChannel(chanelID,channelName, importancia)

                                val manager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                                manager.createNotificationChannel(canal)

                                val notification = NotificationCompat.Builder(this, chanelID).also { noti->
                                    noti.setContentTitle("METAL SLUG APP")
                                    noti.setContentText("Se ha iniciado la sesion ")
                                    noti.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                                }.build()

                                val notificationManager = NotificationManagerCompat.from(applicationContext)
                                notificationManager.notify(1, notification)

                            }
                        }else{
                            Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

            }else{
                Toast.makeText(this, "Los campos son obligatorios :[" , Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val conpass = prefs.getString("conpass", null)

        if(email != null && conpass != null){
            binding.authLayout.visibility = View.INVISIBLE
            showHome(email, conpass)

        }

    }
    override fun onStart(){
        super.onStart()
        binding.authLayout.visibility = View.VISIBLE
    }

    private fun showHome(email: String, conpass: String){
        val homeIntent = Intent(this, Perfil::class.java).apply {
            putExtra("email", email)
            putExtra("conpass", conpass)
        }
        startActivity(homeIntent)
    }


}