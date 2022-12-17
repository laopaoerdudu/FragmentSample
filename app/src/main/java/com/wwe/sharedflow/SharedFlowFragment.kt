package com.wwe.sharedflow

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.wwe.CommonFragment
import kotlinx.coroutines.launch

class SharedFlowFragment : CommonFragment("SharedFlow") {
    private val viewModel by viewModels<SharedFlowViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            edit.addTextChangedListener {
                viewModel.username.value = it?.toString() ?: ""
            }
        }

        lifecycleScope.launch {
            viewModel.headerText
                .flowWithLifecycle(lifecycle)
                .collect {
                    header.text = it
                }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.showSnackBar.collect { showSnackBar(it) }
        }

        update.setOnClickListener {
            closeKeyboard(edit)
            viewModel.updateHeaderClick()
        }
        showSnackbar.setOnClickListener {
            viewModel.showSnackbarClick()
        }
    }
}