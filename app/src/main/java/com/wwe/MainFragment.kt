package com.wwe

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**  */
class MainFragment(private val data: String?) : Fragment(R.layout.fragment_sample_three) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("WWE", "FragmentFour onCreate")
    }

    /** 官方正在逐渐去掉 Fragment 与 Activity 之间的耦合，一个更加独立的 Fragment 更利于复用和测试，因此 onActivityCreated 被废除 */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("WWE", "FragmentFour onAttach")
        requireActivity().lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    owner.lifecycle.removeObserver(this)
                    // 获得 Activity 的 onCreate 事件通知
                }
            })
    }

    override fun onResume() {
        super.onResume()
        Log.i("WWE", "FragmentFour onResume")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tvContent).text = data.orEmpty()
    }
}