package com.wwe.correct

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wwe.R

class CorrectOptionFragment : Fragment(R.layout.fragment_option) {
    private val mViewModel by viewModels<CorrectViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel.correctRandomNumber.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.content).text = it.toString()
            Toast.makeText(requireContext(), "正确示例值发生变化 $it", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<AppCompatButton>(R.id.button).apply {
            text = "正确示例"
            setOnClickListener {
                mViewModel.triggerEvent()
            }
        }
    }
}