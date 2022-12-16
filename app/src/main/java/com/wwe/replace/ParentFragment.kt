package com.wwe.replace

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.wwe.BaseFragment
import com.wwe.R
import com.wwe.databinding.FragentParentBinding

class ParentFragment : BaseFragment<FragentParentBinding>(R.layout.fragent_parent) {
    override fun initBinding(view: View): FragentParentBinding = FragentParentBinding.bind(view)
    var label = ""

    override fun init(savedInstanceState: Bundle?) {
        Log.i(TAG, "init: $label")

        binding.edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //为成员赋值
                label = s.toString()
                binding.text.text = label
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        binding.button.setOnClickListener {
            parentFragmentManager.commit {
                addToBackStack(null)
                replace<ChildFragment>(
                    R.id.container
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "ParentFragment onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "ParentFragment onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "ParentFragment onDetach: ")
    }

    companion object {
        private const val TAG = "WWE"
    }
}
