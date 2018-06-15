package org.demo.weatherapp.util

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
}