package id.go.blitarkab.bpbd.presentation.ui.main.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface RegisterContract {

    interface View : BaseView {
        fun onRegisterFinished(result: Result<Account>)
        fun openLoginPage()
        fun openUserPage()
    }

    interface Presenter : BasePresenter {
        val nameObservable: MutableLiveData<String>
        val emailObservable: MutableLiveData<String>
        val genderObservable: MutableLiveData<String>
        val nikObservable: MutableLiveData<String>
        val phoneObservable: MutableLiveData<String>
        val passwordObservable: MutableLiveData<String>

        val eventRegisterObservable: LiveData<Event<Result<Account>>>

        fun register()
    }

}