package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class DashboardFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root

//        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
        setUp()
    }
    private  fun setUp(){
        binding.btnGuardar.setOnClickListener{
            db.collection("personajes").document(binding.txtNombre.text.toString()).set(
                hashMapOf(
                    "nombre" to binding.txtNombre.text.toString(),
                    "arma" to binding.txtArma.text.toString(),
                    "vida" to binding.txtVida.text.toString(),
                    "danio" to binding.txtdanio.text.toString(),
                )
            )
        }
        binding.btnBorrar.setOnClickListener{
            db.collection("personajes").document(binding.txtNombre.text.toString()).delete()

        }
        binding.btnBuscar.setOnClickListener{
            db.collection("personajes").document(binding.txtNombre.text.toString()).get().addOnCompleteListener{

                binding.txtNombre.setText(it.result.get("nombre") as String?)
                binding.txtArma.setText(it.result.get("arma") as String?)
                binding.txtVida.setText(it.result.get("vida") as String?)
                binding.txtdanio.setText(it.result.get("danio") as String?)
            }

        }
    }
}