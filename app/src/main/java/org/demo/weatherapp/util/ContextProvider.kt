package org.demo.weatherapp.util

import android.app.Activity
import android.content.Context
import java.lang.ref.WeakReference

object ContextProvider {

    private var contextRef: WeakReference<Context?> = WeakReference(null)

    var context: Context?
        /**
         * Sets a new context instance.
         */
        set(context) {
            contextRef = WeakReference(context)
        }
        /**
         * Returns the current context object.
         */
        get() = contextRef.get()

    /**
     * Returns the current context instance as an activity.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Activity> getActivity(): T? {
        return contextRef.get() as T?
    }
}