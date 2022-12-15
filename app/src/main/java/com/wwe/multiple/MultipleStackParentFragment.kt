package com.wwe.multiple

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.commitNow
import com.wwe.BaseStackFragment
import com.wwe.R
import com.wwe.databinding.FragmentMultipleParentBinding
import java.util.*
import kotlin.collections.ArrayList

class MultipleStackParentFragment :
    BaseStackFragment<FragmentMultipleParentBinding>(R.layout.fragment_multiple_parent) {
    override fun initBinding(view: View): FragmentMultipleParentBinding =
        FragmentMultipleParentBinding.bind(view)

    /**
     * 返回栈顺序,存储返回栈id
     */
    private val mOrderStack = ArrayDeque<Int>()

    private val mStackList = ArrayList<NavHostFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //拦截返回键
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!mOrderStack.isNullOrEmpty()) {
                        //移除栈顶 stackId
                        mOrderStack.removeFirst()
                        if (mOrderStack.isNotEmpty()) {
                            //将新的栈顶 显示
                            binding.tabs.check(mOrderStack.first)
                            return
                        }
                    }
                    requireActivity().supportFragmentManager.popBackStack()
                }
            })
    }

    override fun init(savedInstanceState: Bundle?) {

        //add NavHostFragment
        mStackIds.forEachIndexed { index, id ->
            childFragmentManager.commitNow {
                val fragment = NavHostFragment.newInstance(index, name(id))
                mStackList.add(fragment)
                add(R.id.inner_container, fragment, fragment.stableTag)
            }
        }
        binding.tabs.addOnButtonCheckedListener { _, checkId, isChecked ->
            if (isChecked) {
                //被选中的 先出栈，再入栈顶
                mOrderStack.remove(checkId)
                mOrderStack.push(checkId)
                childFragmentManager.commitNow {
                    mStackList.forEach { item ->
                        if (name(checkId) == item.name) {
                            attach(item)
                            setPrimaryNavigationFragment(item)
                        } else {
                            detach(item)
                        }
                    }
                }
            }
        }
        //默认选中 first
        binding.tabs.check(mStackIds[0])
    }
}