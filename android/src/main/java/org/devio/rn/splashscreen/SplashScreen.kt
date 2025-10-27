/**
 * SplashScreen from：http://attarchi.github.io
 * Author: Attarchi
 * GitHub: https://github.com/attarchi
 * Swift version by: React Native Community
 */
package org.devio.rn.splashscreen

import android.animation.Animator
import android.app.Activity
import android.app.Dialog
import android.os.Build
import com.airbnb.lottie.LottieAnimationView
import java.lang.ref.WeakReference

object SplashScreen {
    private var mSplashDialog: Dialog? = null
    private var mActivity: WeakReference<Activity>? = null
    private var isAnimationFinished = false
    private var waiting = false
    private var forceToCloseByHideMethod = false
    private var animationStartTime: Long = 0L

    @JvmStatic
    fun show(
        activity: Activity?,
        themeResId: Int = R.style.SplashScreen_SplashTheme,
        lottieId: Int,
        forceToCloseByHideMethod: Boolean = false
    ) {
        if (activity == null) {
            println("SplashScreen: ERROR - Activity is null")
            return
        }

        mActivity = WeakReference(activity)
        this.forceToCloseByHideMethod = forceToCloseByHideMethod
        this.isAnimationFinished = false

        activity.runOnUiThread {
            if (activity.isFinishing) return@runOnUiThread

            // Create dialog with splash theme
            mSplashDialog = Dialog(activity, themeResId).apply {
                setContentView(R.layout.launch_screen)
                setCancelable(false)
            }

            val lottie = mSplashDialog?.findViewById<LottieAnimationView>(lottieId)

            // Let XML control repeatCount, speed, etc.
            // (So don't override repeatCount or speed here)

            // Reset animation progress
            lottie?.cancelAnimation()
            lottie?.progress = 0f

            // Track start time
            animationStartTime = 0L

            lottie?.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    animationStartTime = System.currentTimeMillis()
                    println("SplashScreen: Animation started")
                }

                override fun onAnimationEnd(animation: Animator) {
                    println("SplashScreen: Animation ended")

                    if (!forceToCloseByHideMethod) {
                        val animationDuration =
                            (System.currentTimeMillis() - animationStartTime) / 1000.0
                        setAnimationFinished(true)

                        // Only hide automatically if not forced by JS
                        if (animationDuration > 0.5) {
                            hideSplashScreen()
                        } else {
                            lottie?.postDelayed({
                                hideSplashScreen()
                            }, ((2.0 - animationDuration) * 1000).toLong())
                        }
                    }
                }

                override fun onAnimationCancel(animation: Animator) {
                    println("SplashScreen: Animation cancelled")
                }

                override fun onAnimationRepeat(animation: Animator) {
                    println("SplashScreen: Animation repeated")
                }
            })

            // Start animation if not running
            if (lottie?.isAnimating == false) {
                lottie.playAnimation()
            }

            if (mSplashDialog?.isShowing == false) {
                mSplashDialog?.show()
            }
        }
    }

    @JvmStatic
    fun setAnimationFinished(flag: Boolean) {
        isAnimationFinished = flag
    }

    /**
     * Hides splash screen when called from JS.
     * Works only if forceToCloseByHideMethod == true
     */
    @JvmStatic
    fun hide(activity: Activity?) {
        if (forceToCloseByHideMethod) {
            hideSplashScreen()
        } else {
            println("SplashScreen: JS hide ignored (forceToCloseByHideMethod=false)")
        }
    }

    private fun hideSplashScreen() {
        val _activity = mActivity?.get() ?: return

        _activity.runOnUiThread {
            if (mSplashDialog != null && mSplashDialog?.isShowing == true) {
                var isDestroyed = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroyed = _activity.isDestroyed
                }

                if (!_activity.isFinishing && !isDestroyed) {
                    mSplashDialog?.dismiss()
                    mSplashDialog = null
                    waiting = true
                    println("SplashScreen: Splash hidden")
                }
            }
        }
    }

    @JvmStatic
    fun setForceToCloseByHideMethod(flag: Boolean) {
        forceToCloseByHideMethod = flag
    }
}
