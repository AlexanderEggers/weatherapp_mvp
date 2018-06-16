package org.demo.weatherapp

import com.google.gson.Gson
import com.google.gson.JsonIOException
import org.demo.weatherapp.api.AppExecutor
import org.demo.weatherapp.api.NetworkInterface
import org.demo.weatherapp.api.WeatherRepository
import org.demo.weatherapp.model.WeatherModel
import org.demo.weatherapp.util.ContextProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

@RunWith(PowerMockRunner::class)
@PrepareForTest(value = [Gson::class, WeatherRepository::class, Response::class, ContextProvider::class])
@PowerMockIgnore("javax.net.ssl.*")
class WeatherRepositoryTest {

    @Mock
    private lateinit var presenterMock: WeatherModelContract.Presenter
    @Mock
    private lateinit var gsonMock: Gson
    @Mock
    private lateinit var cacheControllerMock: WeatherModelContract.Cache
    @Mock
    private lateinit var networkInterfaceMock: NetworkInterface

    @Mock
    private lateinit var callMock: Call<String>
    @Mock
    private lateinit var responseMock: Response<String>

    @Mock
    private lateinit var appExecutorMock: AppExecutor
    @Mock
    private lateinit var workerThreadMock: Executor
    @Mock
    private lateinit var mainThreadMock: Executor

    @Mock
    private lateinit var weatherModelMock: WeatherModel

    private val weatherRepository = WeatherRepository()

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)

        Whitebox.setInternalState(appExecutorMock, "workerThread", workerThreadMock)
        Whitebox.setInternalState(appExecutorMock, "mainThread", mainThreadMock)

        Whitebox.setInternalState(weatherRepository, "gson", gsonMock)
        Whitebox.setInternalState(weatherRepository, "cacheController", cacheControllerMock)
        Whitebox.setInternalState(weatherRepository, "appExecutor", appExecutorMock)
        Whitebox.setInternalState(weatherRepository, "networkInterface", networkInterfaceMock)
    }

    @Test
    fun testDefaultFlow() {
        `when`(responseMock.isSuccessful).thenReturn(true)
        `when`(responseMock.body()).thenReturn("Body stuff")
        `when`(gsonMock.fromJson("Body stuff", WeatherModel::class.java)).thenReturn(weatherModelMock)

        initTest()

        verify(cacheControllerMock, Mockito.times(1)).getCache()
        verify(cacheControllerMock, Mockito.times(1)).updateCacheTime()
        verify(cacheControllerMock, Mockito.times(1)).saveCache("Body stuff")

        verify(presenterMock, Mockito.times(1)).presentData(weatherModelMock)
    }

    @Test
    fun testNotSuccessfulRequest() {
        `when`(responseMock.isSuccessful).thenReturn(false)
        `when`(responseMock.body()).thenReturn(null)

        initTest()

        verify(presenterMock, Mockito.times(1)).presentError()
    }

    @Test
    fun testNullBody() {
        `when`(responseMock.isSuccessful).thenReturn(true)
        `when`(responseMock.body()).thenReturn(null)

        initTest()

        verify(presenterMock, Mockito.times(1)).presentError()
    }

    @Test
    fun testWithRequestException() {
        initTest()
        `when`(callMock.execute()).thenThrow(IOException("Test request exception"))

        verify(presenterMock, Mockito.times(1)).presentError()
    }

    @Test
    fun testWithGsonException() {
        `when`(responseMock.isSuccessful).thenReturn(true)
        `when`(responseMock.body()).thenReturn("Body stuff")
        `when`(gsonMock.fromJson("Body stuff", WeatherModel::class.java)).thenThrow(JsonIOException("Test gson exception"))

        initTest()

        verify(presenterMock, Mockito.times(1)).presentError()
    }

    private fun initTest() {
        `when`(callMock.execute()).thenReturn(responseMock)
        `when`(networkInterfaceMock.getWeatherData(BuildConfig.apiKey)).thenReturn(callMock)

        weatherRepository.getWeatherData(presenterMock)

        val captor = ArgumentCaptor.forClass(Runnable::class.java)
        verify(appExecutorMock.workerThread, times(1)).execute(captor.capture())
        captor.value.run()

        verify(appExecutorMock.mainThread, times(1)).execute(captor.capture())
        captor.value.run()
    }
}