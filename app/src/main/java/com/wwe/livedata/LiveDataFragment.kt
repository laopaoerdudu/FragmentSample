package com.wwe.livedata

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.wwe.CommonFragment

class LiveDataFragment : CommonFragment("LiveData") {

    private val viewModel by viewModels<LiveDataViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit.addTextChangedListener { viewModel.updateUserName(it?.toString().orEmpty()) }

        viewModel.userName.observe(viewLifecycleOwner) {
            closeKeyboard(edit)
            Snackbar.make(edit, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.headerText.observe(viewLifecycleOwner) { header.text = it }
        viewModel.showSnackBar.observe(viewLifecycleOwner) { showSnackBar(it) }

        update.setOnClickListener {
            closeKeyboard(edit)
            viewModel.updateHeaderClick()
        }
        showSnackBar.setOnClickListener {
            viewModel.showSnackBarClick()
        }
    }
}