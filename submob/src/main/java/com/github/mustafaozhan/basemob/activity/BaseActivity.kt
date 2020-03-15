package com.github.mustafaozhan.basemob.activity

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.mustafaozhan.basemob.R
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Mustafa Ozhan on 7/10/18 at 9:37 PM on Arch Linux wit Love <3.
 */
abstract class BaseActivity : AppCompatActivity() {

    @IdRes
    open var containerId: Int = R.id.content

    protected val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected fun setHomeAsUpEnabled(enabled: Boolean) =
        supportActionBar?.setDisplayHomeAsUpEnabled(enabled)

    fun replaceFragment(fragment: Fragment?, withBackStack: Boolean) =
        fragment?.let {
            supportFragmentManager.beginTransaction().apply {

                if (supportFragmentManager.fragments.size > 0) {
                    setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                }

                replace(containerId, it, it.tag)
                if (withBackStack) addToBackStack(it.tag)
                commit()
            }
        }

    override fun onDestroy() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
