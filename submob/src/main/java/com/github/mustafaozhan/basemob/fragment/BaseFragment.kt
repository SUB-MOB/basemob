/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package com.github.mustafaozhan.basemob.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.github.mustafaozhan.basemob.R
import com.github.mustafaozhan.basemob.activity.BaseActivity
import dagger.android.support.AndroidSupportInjection

@Suppress("unused")
abstract class BaseFragment : Fragment() {

    val fragmentTag: String = this.javaClass.simpleName

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    protected fun getBaseActivity() = activity as? BaseActivity

    protected fun navigate(
        currentDestinationId: Int,
        navDirections: NavDirections
    ) = findNavController().let {
        if (it.currentDestination?.id == currentDestinationId) {
            it.navigate(
                navDirections, NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.enter_from_right)
                .setExitAnim(R.anim.exit_to_left)
                .setPopEnterAnim(R.anim.enter_from_left)
                .setPopExitAnim(R.anim.exit_to_right)
                .build()
            )
        }
    }
}
