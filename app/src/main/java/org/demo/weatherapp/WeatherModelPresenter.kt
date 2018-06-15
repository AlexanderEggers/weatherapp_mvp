package org.demo.weatherapp

import org.demo.weatherapp.api.WeatherRepository
import org.demo.weatherapp.model.WeatherModel
import org.demo.weatherapp.util.WeatherIconUtil
import java.text.DateFormat
import java.util.*

class WeatherModelPresenter
constructor(private val view: WeatherModelContract.View) : WeatherModelContract.Presenter {

    private val weatherRepository = WeatherRepository

    override fun retrieveData() {
        weatherRepository.getWeatherData(this)
    }

    override fun presentData(weatherModel: WeatherModel) {
        view.setCityName("${weatherModel.cityName?.toUpperCase(Locale.US)
                ?: ""}, ${weatherModel.systemData?.country ?: ""}")

        view.setTemp(String.format("%.2f", weatherModel.data?.temp ?: "?") + "°")
        view.setDescription(weatherModel.condition?.get(0)?.description?.toUpperCase(Locale.US)
                ?: "")
        view.setHumidity("Humidity: ${weatherModel.data?.humidity ?: ""}%")
        view.setPressure("Pressure: ${weatherModel.data?.pressure ?: ""} hPa")

        val df = DateFormat.getDateTimeInstance()
        view.setUpdateTime("Last update: " + df.format(Date(weatherModel.dataTime * 1000)))

        view.setWeatherIcon(WeatherIconUtil.setWeatherIcon(
                weatherModel.condition?.get(0)?.conditionId ?: 0,
                weatherModel.systemData?.sunrise ?: 0 * 1000,
                weatherModel.systemData?.sunset ?: 0 * 1000))

        view.showContent()
    }

    override fun presentError() {
        view.showError()
    }
}