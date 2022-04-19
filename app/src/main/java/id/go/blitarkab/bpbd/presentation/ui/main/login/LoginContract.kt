package id.go.blitarkab.bpbd.presentation.ui.main.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface LoginContract {

    interface View : BaseView {
        fun onLoginFinished(result: Result<Account>)
        fun openForgotPasswordPage()
        fun openRegisterPage()
        fun openUserPage()
        fun openOperatorPage()
    }

    interface Presenter : BasePresenter {
        val emailObservable: MutableLiveData<String>
        val passwordObservable: MutableLiveData<String>

        val eventLoginObservable: LiveData<Event<Result<Account>>>

        fun login()
    }

}