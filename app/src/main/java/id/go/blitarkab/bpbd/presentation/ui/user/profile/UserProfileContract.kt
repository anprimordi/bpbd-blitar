package id.go.blitarkab.bpbd.presentation.ui.user.profile

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserProfileContract {

    interface View : BaseView {
        fun fetchAccount(result: Result<Account>)
        fun onLogoutFinished(result: Result<Unit>)
        fun showLogoutConfirmationDialog()
        fun openLoginPage()
        fun openProfileDetailPage()
        fun openReviewAppPage()
        fun openAboutPage()
    }

    interface Presenter : BasePresenter {
        val accountObservable: LiveData<Result<Account>>
        val eventLogoutObservable: LiveData<Event<Result<Unit>>>

        fun loadData()
        fun logout()
    }

}