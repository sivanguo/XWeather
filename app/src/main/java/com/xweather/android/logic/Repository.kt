package com.xweather.android.logic

import androidx.lifecycle.liveData
import com.xweather.android.logic.model.Place
import com.xweather.android.logic.model.Weather
import com.xweather.android.logic.network.XWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeReponse = XWeatherNetwork.searchPlaces(query)
        if (placeReponse.status == "ok") {
            val places = placeReponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("fali ${placeReponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async { XWeatherNetwork.getRealtimeWeather(lng, lat) }
            val deferredDaily = async { XWeatherNetwork.getDailyWeather(lng, lat) }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(RuntimeException("realtime : ${realtimeResponse.status}   daily : ${dailyResponse.status}"))
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result= try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }



}