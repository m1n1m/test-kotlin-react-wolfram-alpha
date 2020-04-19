package com.example.calc.service.impl.rest.wolfram

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WolframAlphaFullResultsApi {
    @GET("query")
    fun query(
            @Query("format") format: String,
            @Query("output") output: String,
            @Query("appid") appid: String,
            @Query("input") input: String): Call<JsonElement>

    companion object {
        const val BASE_URL = "https://api.wolframalpha.com/v2/"
    }
}