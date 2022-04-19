package id.go.blitarkab.bpbd.presentation.ui.main.splash

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface SplashContract {

    interface View : BaseView {
        fun fetchAccountState(account: Account?)
        fun openLandingPage()
        fun openUserPage()
        fun openOperatorPage()
    }

    interface Presenter : BasePresenter {
        val accountStateObservable: LiveData<Account?>
        fun loadAccount()
    }

}