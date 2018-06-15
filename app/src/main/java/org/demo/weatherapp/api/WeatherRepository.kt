package org.demo.weatherapp.api

import android.util.Log
import com.google.gson.Gson
import org.demo.weatherapp.BuildConfig
import org.demo.weatherapp.WeatherModelContract
import org.demo.weatherapp.model.WeatherModel
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

object WeatherRepository {

    private val fetchingData = AtomicBoolean(false)
    private val gson: Gson = Gson()
    private val appExecutor = AppExecutor
    private val cacheController = CacheController

    private lateinit var presenter: WeatherModelContract.Presenter

    fun getWeatherData(presenter: WeatherModelContract.Presenter) {
        this.presenter = presenter

        if (fetchingData.compareAndSet(false, true)) {
            appExecutor.workerThread.execute {
                val oldData = cacheController.getCache()
                if (oldData == null || cacheController.isCacheTooOld()) {
                    fetchFromNetwork()
                } else {
                    prepareData(oldData)
                    fetchingData.set(false)
                }
            }
        }
    }

    private fun fetchFromNetwork() {
        val networkInterface = Retrofit.Builder().apply {
            baseUrl("http://api.openweathermap.org/data/2.5/")
            addConverterFactory(ScalarsConverterFactory.create())
        }.build().create(NetworkInterface::class.java)

        try {
            val networkResponse = networkInterface.getWeatherData(BuildConfig.apiKey)
            val response = networkResponse.execute()

            if (response?.isSuccessful == true) {
                response.body()?.let {
                    cacheController.updateCacheTime()
                    cacheController.saveCache(it)
                    prepareData(it)
                }
            } else showError()
        } catch (e: IOException) {
            Log.e(WeatherRepository::class.java.name, e.message)
            e.printStackTrace()
            showError()
        }

        fetchingData.set(false)
    }

    private fun prepareData(data: String) {
        try {
            gson.fromJson<WeatherModel>(data, WeatherModel::class.java)?.let {
                appExecutor.mainThread.execute {
                    presenter.presentData(it)
                }
            } ?: showError()
        } catch (e: Exception) {
            Log.e(WeatherRepository::class.java.name, e.message)
            e.printStackTrace()
            showError()
        }
    }

    private fun showError() {
        appExecutor.mainThread.execute {
            presenter.presentError()
        }
    }
}