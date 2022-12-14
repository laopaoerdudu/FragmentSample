package com.wwe

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class HSFragmentFactory(private val arg: Any) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val clazz = loadFragmentClass(classLoader, className)
        if (clazz == MainFragment::class.java) {
            return MainFragment(arg as? String)
        }
        return super.instantiate(classLoader, className)
    }
}