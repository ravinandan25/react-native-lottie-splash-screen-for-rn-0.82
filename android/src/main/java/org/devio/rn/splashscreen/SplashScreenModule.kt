package org.devio.rn.splashscreen

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import android.app.Activity

/**
 * SplashScreen
 * 启动屏
 * Compatible with React Native 0.82
 */
class SplashScreenModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "SplashScreen"
    }

    /**
     * Hide the splash screen
     */
    @ReactMethod
    fun hide() {
        val activity: Activity = reactApplicationContext.currentActivity ?: return
        SplashScreen.hide(activity)
    }
}
