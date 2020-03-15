package com.github.mustafaozhan.basemob.viewmodel

import androidx.lifecycle.ViewModel
import com.github.mustafaozhan.basemob.extension.applySchedulers
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Mustafa Ozhan on 7/10/18 at 9:40 PM on Arch Linux wit Love <3.
 */
abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    protected fun <T> subscribeService(
        serviceObservable: Observable<T>,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) = compositeDisposable.add(
        serviceObservable.applySchedulers().subscribe(onNext, onError)
    )

    override fun onCleared() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onCleared()
    }
}
