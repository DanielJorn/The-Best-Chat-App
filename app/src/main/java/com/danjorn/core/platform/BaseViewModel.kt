package com.danjorn.core.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danjorn.core.exception.Failure
import javax.inject.Inject

open class BaseViewModel
@Inject constructor() : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}