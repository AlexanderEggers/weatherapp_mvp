package org.demo.weatherapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.demo.weatherapp.util.ContextProvider

class App : Application(), Application.ActivityLifecycleCallbacks {

    private val contextProvider = ContextProvider

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        setContext(activity)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {
        setContext(activity)
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {}

    override fun onActivityDestroyed(activity: Activity) {}

    /**
     * Assigns a context object to the context provider so it can be used for dependencies that
     * need an (activity) context.
     *
     * @param activity an Activity object
     */
    private fun setContext(activity: Activity) {
        contextProvider.context = activity
    }
}