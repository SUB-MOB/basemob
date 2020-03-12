package com.github.mustafaozhan.basemob.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.mustafaozhan.basemob.activity.BaseActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Mustafa Ozhan on 7/10/18 at 9:38 PM on Arch Linux wit Love <3.
 */
@Suppress("TooManyFunctions")
abstract class BaseFragment : Fragment() {

    protected val compositeDisposable by lazy { CompositeDisposable() }

    val fragmentTag: String = this.javaClass.simpleName

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onDestroy()
    }

    @Suppress("SameParameterValue")
    protected fun replaceFragment(
        fragment: Fragment,
        withBackStack: Boolean
    ) = getBaseActivity()?.replaceFragment(fragment, withBackStack)

    protected fun getBaseActivity() = activity as? BaseActivity
}
