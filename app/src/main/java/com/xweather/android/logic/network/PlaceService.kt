package com.xweather.android.logic.network

import com.xweather.android.XWeatherApplication
import com.xweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PlaceService {

    @GET("v2/place?token=${XWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}