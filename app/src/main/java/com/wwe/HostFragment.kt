package com.wwe

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.wwe.livedata.LiveDataFragment
import com.wwe.sharedflow.SharedFlowFragment
import com.wwe.stateflow.StateFlowFragment

class HostFragment : Fragment(R.layout.fragment_host) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.liveDataClick).setOnClickListener { liveDataClick(it) }
        view.findViewById<View>(R.id.stateFlowClick).setOnClickListener { stateFlowClick(it) }
        view.findViewById<View>(R.id.sharedFlowClick).setOnClickListener { shareFlowClick(it) }
    }

    private fun liveDataClick(view: View) = parentFragmentManager.commit {
        addToBackStack(null)
        replace<LiveDataFragment>(R.id.container)
    }

    private fun stateFlowClick(view: View) = parentFragmentManager.commit {
        addToBackStack(null)
        replace<StateFlowFragment>(R.id.container)
    }

    private fun shareFlowClick(view: View) = parentFragmentManager.commit {
        addToBackStack(null)
        replace<SharedFlowFragment>(R.id.container)
    }
}