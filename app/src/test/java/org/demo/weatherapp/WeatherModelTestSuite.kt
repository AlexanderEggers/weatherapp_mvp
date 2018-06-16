package org.demo.weatherapp

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(value = [
    CacheControllerTest::class,
    WeatherModelPresenterTest::class,
    WeatherRepositoryTest::class])
class WeatherModelTestSuite