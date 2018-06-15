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
        fun setCityName(cityName: String)

        /**
         * Sets the time when this weather data has been generated.
         */
        fun setUpdateTime(updateTime: String)

        /**
         * Sets the weather icon inside the view.
         */
        fun setWeatherIcon(weatherIcon: String)

        /**
         * Sets the temperature inside the view.
         */
        fun setTemp(temp: String)

        /**
         * Sets the description inside the view.
         */
        fun setDescription(description: String)

        /**
         * Sets the humidity inside the view.
         */
        fun setHumidity(humidity: String)

        /**
         * Sets the pressure inside the view.
         */
        fun setPressure(pressure: String)
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
}