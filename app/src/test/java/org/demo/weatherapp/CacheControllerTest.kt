package org.demo.weatherapp

import android.content.Context
import android.content.SharedPreferences
import junit.framework.Assert.assertEquals
import org.demo.weatherapp.api.CacheController
import org.demo.weatherapp.util.ContextProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class CacheControllerTest {

    private val intervalHour = 3600000

    private val cacheController = CacheController()
    private val contextProvider = ContextProvider

    private val context: Context = mock(Context::class.java)
    private val sharedPreferences = mock(SharedPreferences::class.java)
    private val sharedPreferencesEditorMock = mock(SharedPreferences.Editor::class.java)

    @Before
    fun beforeTest() {
        initTest()
    }

    @Test
    fun testCacheIsTooOld() {
        `when`(sharedPreferences.getLong("CACHED_DATA_TIME_STAMP", 0))
                .thenReturn(System.currentTimeMillis() - intervalHour)

        val actual = cacheController.isCacheTooOld()
        assertEquals(true, actual)
    }

    @Test
    fun testNoCache() {
        `when`(sharedPreferences.getLong("CACHED_DATA_TIME_STAMP", 0))
                .thenReturn(0)

        val actual = cacheController.isCacheTooOld()
        assertEquals(true, actual)
    }

    @Test
    fun testCacheIsNotTooOld() {
        `when`(sharedPreferences.getLong("CACHED_DATA_TIME_STAMP", 0))
                .thenReturn(System.currentTimeMillis())

        val actual = cacheController.isCacheTooOld()
        assertEquals(false, actual)
    }

    @Test
    fun testUpdateCacheTime() {
        cacheController.updateCacheTime()
        verify(sharedPreferencesEditorMock).putLong(eq("CACHED_DATA_TIME_STAMP"), anyLong())
    }

    @Test
    fun testSaveCache() {
        cacheController.saveCache("Body test")
        verify(sharedPreferencesEditorMock).putString("CACHED_DATA", "Body test")
    }

    @Test
    fun testGetCache() {
        `when`(sharedPreferences.getString("CACHED_DATA", null))
                .thenReturn("Body test")

        val actual = cacheController.getCache()
        assertEquals("Body test", actual)
    }

    private fun initTest() {
        `when`(sharedPreferencesEditorMock.putLong(anyString(), anyLong()))
                .thenReturn(sharedPreferencesEditorMock)
        `when`(sharedPreferencesEditorMock.putString(anyString(), anyString()))
                .thenReturn(sharedPreferencesEditorMock)

        `when`(sharedPreferences.edit())
                .thenReturn(sharedPreferencesEditorMock)

        `when`(context.getSharedPreferences("org.demo.weatherapp", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences)

        contextProvider.context = context
    }
}