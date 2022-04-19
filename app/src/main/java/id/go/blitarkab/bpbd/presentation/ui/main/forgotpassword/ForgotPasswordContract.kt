package id.go.blitarkab.bpbd.presentation.ui.main.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface ForgotPasswordContract {

    interface View : BaseView {
        fun onForgotPasswordFinished(result: Result<Unit>)
        fun showForgotPasswordSuccessDialog()
    }

    interface Presenter : BasePresenter {
        val emailObservable: MutableLiveData<String>
        val eventForgotPasswordObservable: LiveData<Event<Result<Unit>>>

        fun forgotPassword()
    }

}