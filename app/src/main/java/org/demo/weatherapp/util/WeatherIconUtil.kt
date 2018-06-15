package org.demo.weatherapp.util

import java.util.*

class WeatherIconUtil {

    companion object {

        /**
         * Generates the html code for the weather icon based on the weather font that has been
         * used. The relevant value is based on the weather id and the time of the day (if it's
         * still day or already night).
         */
        fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long): String {
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
}