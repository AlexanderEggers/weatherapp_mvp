package org.demo.weatherapp

import org.demo.weatherapp.model.WeatherModel

interface WeatherModelContract {

    interface View  {
        fun setCityName(cityName: String)
        fun setUpdateTime(updateTime: String)
        fun setWeatherIcon(weatherIcon: String)
        fun setTemp(temp: String)
        fun setDescription(description: String)
        fun setHumidity(humidity: String)
        fun setPressure(pressure: String)

        fun showContent()
        fun showError()
    }

    interface Presenter {
        fun presentData(weatherModel: WeatherModel)

        fun prepareData()

        fun presentError()
    }
}