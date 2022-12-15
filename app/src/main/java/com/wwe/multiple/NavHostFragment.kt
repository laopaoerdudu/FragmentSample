package com.wwe.multiple

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.commitNow
import com.wwe.BaseFragment
import com.wwe.R
import com.wwe.databinding.FragmentStackBinding
import com.wwe.ext.args

class NavHostFragment : BaseFragment<FragmentStackBinding>(R.layout.fragment_stack) {
    override fun initBinding(view: View): FragmentStackBinding = FragmentStackBinding.bind(view)

    val stableTag: String
        get() = "${javaClass.simpleName}-$index"

    internal var index: Int by args()
    var name: String by args()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isEnabled = childFragmentManager.backStackEntryCount > 0
                    if (isEnabled) childFragmentManager.popBackStackImmediate()
                    else requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            })
    }

    override fun init(savedInstanceState: Bundle?) {
        val firstTag = "${name}1"
        if (childFragmentManager.findFragmentByTag(firstTag) == null) {
            Log.i(TAG, "NavHostFragment commitNow: $name")
            childFragmentManager.commitNow {
                val fragment = MultipleStackChildFragment.newInstance(name, 1)
//                setPrimaryNavigationFragment(fragment)
                add(R.id.content, fragment, firstTag)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "NavHostFragment onAttach: $name")

    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "NavHostFragment onDetach: $name")
    }

    companion object {
        internal fun newInstance(index: Int, name: String) =
            NavHostFragment().apply {
                this.index = index
                this.name = name
            }

        private const val TAG = "WWE"
    }
}