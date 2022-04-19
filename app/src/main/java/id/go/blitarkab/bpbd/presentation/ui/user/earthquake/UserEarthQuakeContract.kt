package id.go.blitarkab.bpbd.presentation.ui.user.earthquake

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.EarthQuake
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserEarthQuakeContract {

    interface View : BaseView {
        fun fetchEarthQuake(result: Result<EarthQuake>)
    }

    interface Presenter : BasePresenter {
        val earthQuakeDetailObservable: LiveData<Result<EarthQuake>>
        fun loadData()
    }

}