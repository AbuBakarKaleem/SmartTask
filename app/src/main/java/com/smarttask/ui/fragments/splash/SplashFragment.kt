package com.smarttask.ui.fragments.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.smarttask.base.BaseFragment
import com.smarttask.databinding.FragmentSplashBinding
import com.smarttask.extensions.navigateTo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            navigateTo(SplashFragmentDirections.toTasksFragment())
        }
    }
}