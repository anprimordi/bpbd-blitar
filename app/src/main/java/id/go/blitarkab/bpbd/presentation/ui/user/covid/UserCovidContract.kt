package id.go.blitarkab.bpbd.presentation.ui.user.covid

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Covid
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserCovidContract {

    interface View : BaseView {
        fun fetchCovidDetail(result: Result<Pair<Covid, List<Covid>>>)
    }

    interface Presenter : BasePresenter {
        val covidDetailObservable: LiveData<Result<Pair<Covid, List<Covid>>>>
        fun loadData()
    }

}