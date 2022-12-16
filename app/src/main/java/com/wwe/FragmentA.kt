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
import com.wwe.KeyConstant.Companion.REQUEST_KEY_A
import com.wwe.KeyConstant.Companion.REQUEST_KEY_B

class FragmentA : Fragment(R.layout.fragment_comman) {

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<ConstraintLayout>(R.id.root).setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )
        val content = view.findViewById<AppCompatTextView>(R.id.text)
        content.text = FragmentA::class.simpleName

        view.findViewById<MaterialButton>(R.id.button).setOnClickListener {
            // 发送数据
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY_A, bundleOf(
                    "name" to "RomanA",
                )
            )
        }
        // 接收数据
        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY_B, this
        ) { _, bundle ->
            content.text = bundle.getString(
                "name"
            )
        }
    }
}