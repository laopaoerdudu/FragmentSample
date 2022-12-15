package com.wwe.single

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.commit
import com.wwe.BaseStackFragment
import com.wwe.R
import com.wwe.databinding.FragmentSingleChildBinding
import com.wwe.ext.args

class SingleStackChildFragment :
    BaseStackFragment<FragmentSingleChildBinding>(R.layout.fragment_single_child) {
    override fun initBinding(view: View): FragmentSingleChildBinding =
        FragmentSingleChildBinding.bind(view)

    val stableTag: String
        get() = "${javaClass.simpleName}-$name-$depth"

    private var name: String by args()

    var depth: Int by args()

    override fun init(savedInstanceState: Bundle?) {

        binding.button.text = getString(R.string.fragmentHint1, name(id), getCount(depth))
        binding.button.setOnClickListener {
            if (depth < 13) {
                parentFragmentManager.commit {
                    replace(
                        id,
                        newInstance(
                            name,
                            depth + 1
                        )
                    )
                    addToBackStack(name(id))
                }
            } else {
                Toast.makeText(
                        requireContext(),
                        getString(R.string.done, name(id)),
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }


    companion object {
        fun newInstance(name: String, depth: Int): SingleStackChildFragment =
            SingleStackChildFragment().apply {
                this.name = name
                this.depth = depth
            }
    }
}