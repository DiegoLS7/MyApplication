package com.example.myapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

//import retrofit2.http.GET

interface APIService {
    @GET
    suspend fun getDogsByBreeds(@Url url:String):Response<DogsResponse>
}