package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMenupetBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PetMenu : AppCompatActivity(), SearchView.OnQueryTextListener{

    private lateinit var binding: ActivityMenupetBinding
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override  fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMenupetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }


    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
                .baseUrl( "https://dog.ceo/api/breed/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    private fun searchByName(query:String) {
        CoroutineScope (Dispatchers.IO). launch {
            val call = getRetrofit().create(APIService::class.java).getDogsByBreeds ("$query/images")
            val puppies = call.body()
            runOnUiThread {
                if(call.isSuccessful){
                    val images = puppies?.images ?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                    //show recyclerview
                }else{
                    showError()
                }
            }

        }
        }
    private fun showError(){
        Toast.makeText(this, "ha ocurrido un error", Toast.LENGTH_SHORT).show()


    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}