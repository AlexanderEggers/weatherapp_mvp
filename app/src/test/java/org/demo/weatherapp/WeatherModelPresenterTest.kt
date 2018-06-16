package org.demo.weatherapp

import junit.framework.Assert.assertEquals
import org.demo.weatherapp.api.WeatherRepository
import org.demo.weatherapp.model.ConditionModel
import org.demo.weatherapp.model.DataModel
import org.demo.weatherapp.model.SystemModel
import org.demo.weatherapp.model.WeatherModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox

@RunWith(PowerMockRunner::class)
@PrepareForTest(WeatherRepository::class)
@PowerMockIgnore("javax.net.ssl.*")
class WeatherModelPresenterTest {

    @Mock
    private lateinit var viewMock : WeatherModelContract.View
    @Mock
    private lateinit var repositoryMock: WeatherModelContract.Repository

    private lateinit var presenter: WeatherModelPresenter
    private lateinit var weatherModel: WeatherModel

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        presenter = WeatherModelPresenter(viewMock)

        Whitebox.setInternalState(presenter, "weatherRepository", repositoryMock)
        initTest()
    }

    @Test
    fun testPrepareData() {
        presenter.prepareData()
        verify(repositoryMock, times(1)).getWeatherData(presenter)
    }

    @Test
    fun testShowContent() {
        presenter.presentData(weatherModel)
        verify(viewMock, times(1)).showContent()
    }

    @Test
    fun testShowError() {
        presenter.presentError()
        verify(viewMock, times(1)).showError()
    }

    @Test
    fun testCityName() {
        presenter.presentData(weatherModel)

        val captor = ArgumentCaptor.forClass(String::class.java)
        verify(viewMock, times(1)).setCityName(captor.capture())

        val value = captor.value
        assertEquals("MELBOURNE, AUS", value)
    }

    /**
     * Note: The weather icon would need much more testing, but for this demo project it should not
     * be needed to test each and every case.
     */
    @Test
    fun testWeatherIcon() {
        presenter.presentData(weatherModel)

        val captor = ArgumentCaptor.forClass(String::class.java)
        verify(viewMock, times(1)).setWeatherIcon(captor.capture())

        val value = captor.value
        assertEquals("&#xf01e;", value)
    }

    @Test
    fun testUpdateTime() {
        presenter.presentData(weatherModel)

        val captor = ArgumentCaptor.forClass(String::class.java)
        verify(viewMock, times(1)).setUpdateTime(captor.capture())

        val value = captor.value
        assertEquals("Last update: 01-Jan-1970 10:25:00", value)
    }

    @Test
    fun testPressure() {
        presenter.presentData(weatherModel)

        val captor = ArgumentCaptor.forClass(String::class.java)
        verify(viewMock, times(1)).setPressure(captor.capture())

        val value = captor.value
        assertEquals("Pressure: 5 hPa", value)
    }

    @Test
    fun testHumidity() {
        presenter.presentData(weatherModel)

        val captor = ArgumentCaptor.forClass(String::class.java)
        verify(viewMock, times(1)).setHumidity(captor.capture())

        val value = captor.value
        assertEquals("Humidity: 10%", value)
    }

    @Test
    fun testTemp() {
        presenter.presentData(weatherModel)

        val captor = ArgumentCaptor.forClass(String::class.java)
        verify(viewMock, times(1)).setTemp(captor.capture())

        val value = captor.value
        assertEquals("20.00°", value)
    }

    @Test
    fun testDescription() {
        presenter.presentData(weatherModel)

        val captor = ArgumentCaptor.forClass(String::class.java)
        verify(viewMock, times(1)).setDescription(captor.capture())

        val value = captor.value
        assertEquals("TEST DESCRIPTION", value)
    }

    private fun initTest() {
        weatherModel = WeatherModel()

        weatherModel.cityName = "Melbourne"
        weatherModel.dataTime = 1500
        weatherModel.id = 2

        val systemData = SystemModel()
        systemData.country = "AUS"
        systemData.sunrise = 0
        systemData.sunset = 1
        weatherModel.systemData = systemData

        val dataModel = DataModel()
        dataModel.humidity = "10"
        dataModel.pressure = "5"
        dataModel.temp = 20.0
        weatherModel.data = dataModel

        val conditionModel = ConditionModel()
        conditionModel.conditionId = 200
        conditionModel.description = "Test Description"

        val conditions = arrayListOf(conditionModel)
        weatherModel.condition = conditions
    }
}