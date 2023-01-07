package com.xweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xweather.android.logic.Repository

class WeatherViewModel:ViewModel() {

    private val localLiveData=MutableLiveData<com.xweather.android.logic.model.Location>()

    var locationLng=""
    var locationLat=""
    var placeName=""

    val weatherLiveData=Transformations.switchMap(localLiveData){
        Repository.refreshWeather(it.lng,it.lat)
    }

    fun refreshWeather(lng:String,lat:String){
        localLiveData.value=com.xweather.android.logic.model.Location(lng, lat)
    }


}