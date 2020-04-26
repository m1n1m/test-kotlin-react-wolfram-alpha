package com.example.calc.service.impl.rest.wolfram

import com.wolfram.alpha.WAQueryResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WolframAlphaFullResultsApi {
    @GET("query")
    fun query(
            @Query("format") format: String,
            @Query("output") output: String,
            @Query("appid") appid: String,
            @Query("input") input: String): Call<WAQueryResult>

    companion object {
        const val BASE_URL = "https://api.wolframalpha.com/v2/"
    }
}