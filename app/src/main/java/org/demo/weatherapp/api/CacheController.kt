package org.demo.weatherapp.api

import android.content.Context
import android.content.SharedPreferences
import org.demo.weatherapp.util.ContextProvider

object CacheController {

    private const val INTERVAL_HOUR = 3600000

    private val contextProvider: ContextProvider = ContextProvider

    fun isCacheTooOld(): Boolean {
        contextProvider.context?.let{
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                    "org.demo.weatherapp", Context.MODE_PRIVATE)

            val cachedTime = sharedPreferences.getLong("CACHED_DATA_TIME_STAMP", 0)
            val currentTime = System.currentTimeMillis()

            return currentTime - cachedTime >= INTERVAL_HOUR
        } ?: return true
    }

    fun updateCacheTime() {
        contextProvider.context?.let{
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                    "org.demo.weatherapp", Context.MODE_PRIVATE)

            sharedPreferences.edit()
                    .putLong("CACHED_DATA_TIME_STAMP", System.currentTimeMillis())
                    .apply()
        }
    }

    fun saveCache(data: String) {
        contextProvider.context?.let{
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                    "org.demo.weatherapp", Context.MODE_PRIVATE)

            sharedPreferences.edit()
                    .putString("CACHED_DATA", data)
                    .apply()
        }
    }

    fun getCache(): String? {
        contextProvider.context?.let{
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                    "org.demo.weatherapp", Context.MODE_PRIVATE)

            return sharedPreferences.getString("CACHED_DATA", null)
        } ?: return null
    }
}