package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityCrearUsuarioBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


import android.content.Intent as Intent1

class CrearUsuario : AppCompatActivity() {

    private lateinit var  binding: ActivityCrearUsuarioBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)

        binding = ActivityCrearUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth =  FirebaseAuth.getInstance()

        binding.btnVolver.setOnClickListener{
            val intent = Intent1(this,  MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnCrearCuenta.setOnClickListener{
            val email = binding.txtEmail.text.toString()
            val conpass = binding.txtPass.text.toString()
            val pass = binding.txtpass2.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty() && conpass.isNotEmpty()){
                if (conpass == pass){
                    firebaseAuth.createUserWithEmailAndPassword(email, conpass).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent1(this,  Login::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    Toast.makeText(this, "Las contraseñas no coinciden" , Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Los campos son obligatorios :[" , Toast.LENGTH_SHORT).show()
            }
        }
    }




}