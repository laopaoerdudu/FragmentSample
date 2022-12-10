package com.wwe

import android.app.Application
import androidx.fragment.app.strictmode.FragmentStrictMode

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        /**
         * 谷歌官方一直致力于对 Fragment API 的优化，希望它更加易用和便于测试。这些已废弃的 API 在未来的版本中将会彻底删除，
         * 所以如果你还在使用着他们，应该尽快予以替换。
         * 官方提供了工具帮助我们发现对于过期 API 的使用，Fragment-1.4.0 之后，我们可以通过全局设置严格模式策略，发现项目中的问题：
         */
        FragmentStrictMode.defaultPolicy =
            FragmentStrictMode.Policy.Builder()
                .detectFragmentTagUsage() //setTargetFragment的使用
                .detectRetainInstanceUsage()//setRetainInstance的使用
                .detectSetUserVisibleHint()//setUserVisibleHint的使用
                .detectTargetFragmentUsage()//setTargetFragment的使用
                .apply {
                    if (BuildConfig.DEBUG) {
                        // Debug 模式下崩溃
                        penaltyDeath()
                    } else {
                        // Release 模式下上报
                        penaltyListener {
                            // FirebaseCrashlytics.getInstance().recordException(it)
                        }
                    }
                }
                .build()
    }
}