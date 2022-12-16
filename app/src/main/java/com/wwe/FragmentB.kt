package com.wwe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class FragmentB : Fragment(R.layout.fragment_comman) {

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<ConstraintLayout>(R.id.root).setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorYellow
            )
        )
        val content = view.findViewById<AppCompatTextView>(R.id.text)
        content.text = FragmentB::class.simpleName

        view.findViewById<MaterialButton>(R.id.button).setOnClickListener {
            // 发送数据
            parentFragmentManager.setFragmentResult(
                KeyConstant.REQUEST_KEY_B, bundleOf(
                    "name" to "RomanB",
                )
            )
        }

        // 接收数据
        parentFragmentManager.setFragmentResultListener(
            KeyConstant.REQUEST_KEY_A, this
        ) { _, bundle ->
            content.text = bundle.getString(
                "name"
            )
        }
    }
}