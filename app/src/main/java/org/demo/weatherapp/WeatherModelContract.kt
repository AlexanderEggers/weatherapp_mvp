package org.demo.weatherapp

import org.demo.weatherapp.model.WeatherModel

/**
 * Interface which is used to communicate between the view and presenter.
 */
interface WeatherModelContract {

    /**
     * Interface which defines the view contract and it's methods which can be from outside the
     * view object.
     */
    interface View  {

        /**
         * Shows the content container inside the view.
         */
        fun showContent()

        /**
         * Shows the error container inside the view.
         */
        fun showError()

        /**
         * Sets the city name inside the view.
         */
        fun setCityName(cityName: String?)

        /**
         * Sets the time when this weather data has been generated.
         */
        fun setUpdateTime(updateTime: String?)

        /**
         * Sets the weather icon inside the view.
         */
        fun setWeatherIcon(weatherIcon: String?)

        /**
         * Sets the temperature inside the view.
         */
        fun setTemp(temp: String?)

        /**
         * Sets the description inside the view.
         */
        fun setDescription(description: String?)

        /**
         * Sets the humidity inside the view.
         */
        fun setHumidity(humidity: String?)

        /**
         * Sets the pressure inside the view.
         */
        fun setPressure(pressure: String?)
    }

    /**
     * Interface which defines the presenter and it's methods which can be from outside the
     * presenter object.
     */
    interface Presenter {

        /**
         * Prepares the data for the view.
         */
        fun prepareData()

        /**
         * Uses the provided [WeatherModel] to present the data to the view.
         */
        fun presentData(weatherModel: WeatherModel)

        /**
         * Present an error to the view.
         */
        fun presentError()
    }

    /**
     * Interface which defines the Repository and it's methods.
     */
    interface Repository {

        /**
         * Checks the current cached weather model data. If the cache is outdated or not existing,
         * the class will try to retrieve new weather data from the server.
         */
        fun getWeatherData(presenter: Presenter)
    }

    /**
     * Interface which defines the Cache related object and it's methods.
     */
    interface Cache {

        /**
         * Returns true if the stored cache is older more than one hour, otherwise false.
         */
        fun isCacheTooOld(): Boolean

        /**
         * Updates the cached time stamp which is used to determine if the cached weather data is still
         * up-to-date.
         */
        fun updateCacheTime()

        /**
         * Saves the provided data inside the shared preference object.
         */
        fun saveCache(data: String)

        /**
         * Returns the current stored weather data. If nothing was found, the method will return null.
         */
        fun getCache(): String?
    }
}