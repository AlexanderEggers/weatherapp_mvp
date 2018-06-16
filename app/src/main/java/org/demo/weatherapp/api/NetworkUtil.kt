package org.demo.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkUtil {

    companion object {

        @JvmStatic
        fun provideNetworkInterface(): NetworkInterface {
            return Retrofit.Builder().apply {
                baseUrl("http://api.openweathermap.org/data/2.5/")
                addConverterFactory(ScalarsConverterFactory.create())
            }.build().create(NetworkInterface::class.java)
        }
    }
}