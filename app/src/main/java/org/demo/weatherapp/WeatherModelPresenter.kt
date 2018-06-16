package org.demo.weatherapp

import org.demo.weatherapp.api.WeatherRepository
import org.demo.weatherapp.model.WeatherModel
import java.text.DateFormat
import java.util.*

class WeatherModelPresenter
constructor(private val view: WeatherModelContract.View) : WeatherModelContract.Presenter {

    private var weatherRepository: WeatherModelContract.Repository = WeatherRepository()

    override fun prepareData() {
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

        view.setWeatherIcon(setWeatherIcon(
                weatherModel.condition?.get(0)?.conditionId ?: 0,
                weatherModel.systemData?.sunrise ?: 0 * 1000,
                weatherModel.systemData?.sunset ?: 0 * 1000))

        view.showContent()
    }

    override fun presentError() {
        view.showError()
    }

    private fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long): String {
        val id = actualId / 100
        var icon = ""
        if (actualId == 800) {
            val currentTime = Date().time
            icon = if (currentTime in sunrise..(sunset - 1)) {
                "&#xf00d;"
            } else {
                "&#xf02e;"
            }
        } else {
            when (id) {
                2 -> icon = "&#xf01e;"
                3 -> icon = "&#xf01c;"
                7 -> icon = "&#xf014;"
                8 -> icon = "&#xf013;"
                6 -> icon = "&#xf01b;"
                5 -> icon = "&#xf019;"
            }
        }
        return icon
    }
}