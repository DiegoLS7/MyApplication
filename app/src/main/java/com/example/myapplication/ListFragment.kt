package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.example.myapplication.databinding.FragmentListBinding
//import com.example.myapplication.models.DogsApi
import com.example.myapplication.models.Mascotas
import com.example.myapplication.models.Personajes
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject

class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ListFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment ESTO NO VA ESTO NO VA ESTO NO VA
        //return inflater.inflate(R.layout.fragment_casa, container, false) ESTO NO VA ESTO NO VA
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
            val query = db.collection("mascotas")
            val options =  FirestoreRecyclerOptions.Builder<Mascotas>().setQuery(query,
                Mascotas::class.java).setLifecycleOwner(this).build()

            val adapter = object: FirestoreRecyclerAdapter<Mascotas, PetViewHolder>(options){
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
                    val view = LayoutInflater.from(context).inflate(R.layout.card_mascotas, parent, false)
                    return PetViewHolder(view)
                }

                override fun onBindViewHolder(holder: PetViewHolder, position: Int, model: Mascotas) {
                    val raza: TextView = holder.itemView.findViewById(R.id.textRaza)
                    val nombre: TextView = holder.itemView.findViewById(R.id.textNombre)
                    val vida: TextView = holder.itemView.findViewById(R.id.textVida)
                    val estilo: TextView = holder.itemView.findViewById(R.id.textEstilo)
                    raza.text = model.raza
                    nombre.text = model.nombre
                    vida.text = model.vida
                    estilo.text = model.estilo
                }

            }

            binding.rvMascotas.adapter = adapter
            binding.rvMascotas.layoutManager = LinearLayoutManager(context)
        }


//
//        dogsRV =  binding.dogsRecyclerView
//        dogNameTest =  binding.dogsNameET
//        searchBt =  binding.searchBt
//
//        dogsRV.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//
//        searchBt.setOnClickListener{
//            var name = dogNameTest.text.toString()
//
//            seachDogs(name)
//        }
//
//    }
//
//    private fun seachDogs(name: String) {
//
//        imageList.clear()
//
//        AndroidNetworking.initialize(this)
//        AndroidNetworking.get("https://dog.ceo/api/breed/$name/images") .setPriority (Priority.HIGH)
//            .build()
//            .getAsString(object : StringRequestListener{
//                override fun onResponse(response: String?) {
//                    val result = JSONObject(response)
//                    val image = result.getJSONArray("message")
//
//                    for(i in 0 until image.length()){
//
//                        val list = image.get(i)
//                        imageList.add(
//                            DogsApi(list.toString())
//                        )
//                    }
//                    dogsRV.adapter = DogsAdapter(this@ListFragment, imageList)
//                }
//
//                override fun onError(anError: ANError?) {
//
//                }
//
//
//            })

}
