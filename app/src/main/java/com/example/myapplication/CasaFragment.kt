package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.example.myapplication.databinding.FragmentCasaBinding
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.example.myapplication.models.Personajes
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class DashViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class CasaFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentCasaBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_casa, container, false)
        _binding = FragmentCasaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
        val query = db.collection("personajes")
        val options =  FirestoreRecyclerOptions.Builder<Personajes>().setQuery(query,Personajes::class.java).setLifecycleOwner(this).build()

        val adapter = object: FirestoreRecyclerAdapter<Personajes,DashViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashViewHolder {
                val view = LayoutInflater.from(context).inflate(R.layout.card_personajes, parent, false)
                return DashViewHolder(view)
            }

            override fun onBindViewHolder(holder: DashViewHolder, position: Int, model: Personajes) {
                val nombre: TextView = holder.itemView.findViewById(R.id.textNombre)
                val arma: TextView = holder.itemView.findViewById(R.id.textArma)
                val vida: TextView = holder.itemView.findViewById(R.id.textVida)
                val danio: TextView = holder.itemView.findViewById(R.id.textDanio)
                nombre.text = model.nombre
                arma.text = model.arma
                vida.text = model.vida
                danio.text = model.danio
            }

        }

        binding.rvPersonaje.adapter = adapter
        binding.rvPersonaje.layoutManager = LinearLayoutManager(context)

    }
}