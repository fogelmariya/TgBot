package com.github.fogelao.api

import com.github.fogelao.entity.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Fogel Artem on 27.05.2018.
 */
interface TrackApi {

    //TODO:Add language support
    @GET("tracking.json.php?pretty=true")
    fun getCodeInfo(@Query("apiKey") apiKey: String = "2c433e74908e8c1beb9334e5c75afd76",
                    @Query("domain") domain: String = "demo.track24.ru",
                    @Query("code") code: String,
                    @Query("lng") language: String = "en"): Call<Response>
}