package com.example.superherohero.data

import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroService {

    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") query: String) : SuperheroResponse

    @GET("{character-id}")
    suspend fun findSuperheroById(@Path("character-id") id: String) : Superhero
}


