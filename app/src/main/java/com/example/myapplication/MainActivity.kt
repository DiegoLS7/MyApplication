package com.example.myapplication

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.databinding.ActivityCrearUsuarioBinding
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*


class MainActivity : AppCompatActivity() {

//    private lateinit var switch: Switch
//    private lateinit var lbBienvenido: TextView
//    private lateinit var ingresarcrede: TextView
//    private lateinit var ingresargoogle: TextView



    private val GOOGLE_SIGN_IN = 100
    private lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        switch = findViewById(R.id.swtichLang)
//        lbBienvenido = findViewById(R.id.TextView)
//        ingresarcrede = findViewById(R.id.btnLogin)
//        ingresargoogle = findViewById(R.id.btnLoginGoogle)
//
//        switch.setOnCheckedChangeListener{ _, isCheked ->
//            if(isCheked){
//                Toast.makeText(this@MainActivity, "Change lenguaje", Toast.LENGTH_SHORT).show()
//                actualizarResource("en")
//            } else {
//                Toast.makeText(this@MainActivity, "Cambio idioma", Toast.LENGTH_SHORT).show()
//                actualizarResource("es")
//            }
//        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this,  Login::class.java)
            startActivity(intent)
    }

        binding.btnLoginGoogle.setOnClickListener{
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }

        binding.btnLoginFacebook.setOnClickListener{
            val intent = Intent(this, PetMenu::class.java)
            startActivity(intent)
        }


    // FUNCION DEL BOTON CREDENCIALES

    }

//     fun actualizarResource(idioma: String) {
//        val recursos = resources
//        val displayMetrics = recursos.displayMetrics
//        val configuracion = resources.configuration
//        configuracion.setLocale(Locale(idioma))
//        recursos.updateConfiguration(configuracion, displayMetrics)
//        configuracion.locale = Locale(idioma)
//         resources.updateConfiguration(configuracion, displayMetrics)
//
//        switch.text = recursos.getString(R.string.leanguaje)
//        lbBienvenido.text = recursos.getString(R.string.lbBienvenido)
//        ingresarcrede.text = recursos.getString(R.string.ingresarcrede)
//        ingresargoogle.text = recursos.getString(R.string.ingresargoogle)
//    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            if(account !=null){
                val task = GoogleAuthProvider.getCredential(account.idToken, null)

                try {

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent(this, Perfil::class.java)
                            startActivity(intent)

                        }else{
                            Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (e: ApiException){
                    Toast.makeText(this, "ERROR AL NO ENCONTRAR CUENTA DE GOOGLE,MAIN :[" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}