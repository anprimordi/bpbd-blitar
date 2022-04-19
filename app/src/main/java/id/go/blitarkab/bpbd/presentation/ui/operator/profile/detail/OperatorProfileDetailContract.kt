package id.go.blitarkab.bpbd.presentation.ui.operator.profile.detail

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface OperatorProfileDetailContract {

    interface View : BaseView {
        fun fetchAccount(result: Result<Account>)
        fun openProfileUpdatePage()
    }

    interface Presenter : BasePresenter {
        val accountObservable: LiveData<Result<Account>>
        fun loadData()
    }

}