package com.wwe.error

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.wwe.R

class ErrorOptionFragment : Fragment(R.layout.fragment_option) {
    private val mViewModel by viewModels<ErrorViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //初始化数据
        mViewModel.onGetNumber()

        //由于每次点击按钮修改了 LiveData 的引用，也即这里只能监听到初始化的数据
        mViewModel.errorRandomNumber.observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.content).text = it.toString()
            Toast.makeText(requireContext(), "错误示例值发生变化 $it", Toast.LENGTH_SHORT).show()
        })
        view.findViewById<AppCompatButton>(R.id.button).apply {
            text = requireContext().getString(R.string.hint_error)
            setOnClickListener {
                mViewModel.onGetNumber()
            }
        }
    }
}