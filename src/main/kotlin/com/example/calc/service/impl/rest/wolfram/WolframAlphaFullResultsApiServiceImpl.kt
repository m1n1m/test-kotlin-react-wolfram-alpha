package com.example.calc.service.impl.rest.wolfram

import com.example.calc.service.rest.wolfram.WolframAlphaFullResultsApiService
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Service
class WolframAlphaFullResultsApiServiceImpl() : WolframAlphaFullResultsApiService {

    private val appId = "AW278K-K77H68RKL6"
    private lateinit var restApi: WolframAlphaFullResultsApi

    init {
        val builder = OkHttpClient.Builder()
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(WolframAlphaFullResultsApi.BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        restApi = retrofit.create(WolframAlphaFullResultsApi::class.java)
    }

    override fun query(input: String): String {
        val response = restApi.query("plaintext", "JSON", appId, input).execute()
        val queryResult = response.body()?.asJsonObject?.get("queryresult")?.asJsonObject
        if (queryResult?.get("success")?.asBoolean == false) {
            return ""
        }
        val pods = queryResult?.get("pods")?.asJsonArray
        val sub = pods?.find { el -> el.asJsonObject.get("id").asString == "Result" }
        return sub?.asJsonObject?.get("subpods")?.asJsonArray?.get(0)?.asJsonObject?.get("plaintext")?.asString ?: ""
    }
}