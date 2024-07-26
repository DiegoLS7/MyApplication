package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.databinding.FragmentPetBinding
import com.google.firebase.firestore.FirebaseFirestore


class PetFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()

    private var _binding: FragmentPetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_casa, container, false)
        _binding = FragmentPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private  fun setUp(){
        binding.btnGuardar.setOnClickListener{
            db.collection("mascotas").document(binding.textRaza.text.toString()).set(
                hashMapOf(
                    "raza" to binding.textRaza.text.toString(),
                    "nombre" to binding.textNombre.text.toString(),
                    "vida" to binding.textVida.text.toString(),
                    "estilo" to binding.textEstilo.text.toString(),
                )
            )
        }
        binding.btnBorrar.setOnClickListener{
            db.collection("mascotas").document(binding.textRaza.text.toString()).delete()
        }
        binding.btnBuscar.setOnClickListener{
            db.collection("mascotas").document(binding.textRaza.text.toString()).get().addOnCompleteListener{

                binding.textRaza.setText(it.result.get("raza") as String?)
                binding.textNombre.setText(it.result.get("nombre") as String?)
                binding.textVida.setText(it.result.get("vida") as String?)
                binding.textEstilo.setText(it.result.get("estilo") as String?)
            }

        }
    }

}