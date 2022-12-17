package com.wwe.livedata

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.wwe.CommonFragment

class LiveDataFragment : CommonFragment("LiveData") {

    private val viewModel by viewModels<LiveDataViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit.addTextChangedListener { viewModel.username.value = it?.toString() }

        viewModel.headerText.observe(viewLifecycleOwner) { header.text = it }
        viewModel.showSnackBar.observe(viewLifecycleOwner) { showSnackBar(it) }

        update.setOnClickListener {
            closeKeyboard(edit)
            viewModel.updateHeaderClick()
        }
        showSnackbar.setOnClickListener {
            viewModel.showSnackBarClick()
        }
    }
}