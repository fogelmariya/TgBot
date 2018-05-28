package com.github.fogelao.presentation

import com.github.fogelao.api.TrackApi
import com.github.fogelao.entity.Response
import com.github.fogelao.presentation.view.MainView
import org.telegram.telegrambots.logging.BotLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Fogel Artem on 27.05.2018.
 */
class Presenter(private val view: MainView) {
    companion object {
        const val BASE_URL = "https://api.track24.ru/"
    }

    private val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrackApi::class.java)!!

    fun track(code: String, chatId: Long) {
        api.getCodeInfo(code = code)
                .enqueue(object : Callback<Response> {

                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        if (response.isSuccessful) {
                            if (response.body()?.data != null) {
                                val data = response.body()?.data
                                val lastPoint = data?.lastPoint
                                if (lastPoint != null) {
                                    val answer = "$data\n$lastPoint"
                                    view.showCodeInfo(answer, chatId)
                                }
//                                if (events != null) {
//                                    val lastEvent = events.last()
//                                    val answer = "$data\n$lastEvent"
//                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<Response>?, t: Throwable) {
                        BotLogger.warn("Presenter", t)
                    }
                })


        view.showCodeInfo("", chatId)
    }
}