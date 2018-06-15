package org.demo.weatherapp

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import android.widget.TextView

class WeatherModelView : AppCompatActivity(), WeatherModelContract.View {

    private lateinit var presenter: WeatherModelContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.weather_icon)?.apply {
            typeface = Typeface.createFromAsset(assets, "fonts/weathericons-regular-webfont.ttf")
        }

        findViewById<View>(R.id.retry_button).setOnClickListener {
            presenter.prepareData()
        }

        presenter = WeatherModelPresenter(this)
        presenter.prepareData()
    }

    override fun showContent() {
        findViewById<View>(R.id.content_container)?.apply {
            visibility = View.VISIBLE
        }

        findViewById<View>(R.id.error_container)?.apply {
            visibility = View.GONE
        }
    }

    override fun showError() {
        findViewById<View>(R.id.content_container)?.apply {
            visibility = View.GONE
        }

        findViewById<View>(R.id.error_container)?.apply {
            visibility = View.VISIBLE
        }
    }

    override fun setCityName(cityName: String) {
        findViewById<TextView>(R.id.city_field)?.apply {
            text = cityName
        }
    }

    override fun setUpdateTime(updateTime: String) {
        findViewById<TextView>(R.id.updated_field)?.apply {
            text = updateTime
        }
    }

    override fun setWeatherIcon(weatherIcon: String) {
        findViewById<TextView>(R.id.weather_icon)?.apply {
            text = Html.fromHtml(weatherIcon)
        }
    }

    override fun setTemp(temp: String) {
        findViewById<TextView>(R.id.current_temperature_field)?.apply {
            text = temp
        }
    }

    override fun setDescription(description: String) {
        findViewById<TextView>(R.id.details_field)?.apply {
            text = description
        }
    }

    override fun setHumidity(humidity: String) {
        findViewById<TextView>(R.id.humidity_field)?.apply {
            text = humidity
        }
    }

    override fun setPressure(pressure: String) {
        findViewById<TextView>(R.id.pressure_field)?.apply {
            text = pressure
        }
    }
}
