package org.demo.weatherapp.api

import android.content.Context
import android.content.SharedPreferences
import org.demo.weatherapp.util.ContextProvider

object CacheController {

    private const val INTERVAL_HOUR = 3600000

    private val contextProvider: ContextProvider = ContextProvider

    /**
     * Returns true if the stored cache is older more than one hour, otherwise false.
     */
    fun isCacheTooOld(): Boolean {
        contextProvider.context?.let{
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                    "org.demo.weatherapp", Context.MODE_PRIVATE)

            val cachedTime = sharedPreferences.getLong("CACHED_DATA_TIME_STAMP", 0)
            val currentTime = System.currentTimeMillis()

            return currentTime - cachedTime >= INTERVAL_HOUR
        } ?: return true
    }

    /**
     * Updates the cached time stamp which is used to determine if the cached weather data is still
     * up-to-date.
     */
    fun updateCacheTime() {
        contextProvider.context?.let{
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                    "org.demo.weatherapp", Context.MODE_PRIVATE)

            sharedPreferences.edit()
                    .putLong("CACHED_DATA_TIME_STAMP", System.currentTimeMillis())
                    .apply()
        }
    }

    /**
     * Saves the provided data inside the shared preference object.
     */
    fun saveCache(data: String) {
        contextProvider.context?.let{
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                    "org.demo.weatherapp", Context.MODE_PRIVATE)

            sharedPreferences.edit()
                    .putString("CACHED_DATA", data)
                    .apply()
        }
    }

    /**
     * Returns the current stored weather data. If nothing was found, the method will return null.
     */
    fun getCache(): String? {
        contextProvider.context?.let{
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                    "org.demo.weatherapp", Context.MODE_PRIVATE)

            return sharedPreferences.getString("CACHED_DATA", null)
        } ?: return null
    }
}