package id.go.blitarkab.bpbd.presentation.ui.user.volcano

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Volcano
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserVolcanoContract {

    interface View : BaseView {
        fun fetchVolcanoList(result: Result<List<Volcano>>)
    }

    interface Presenter : BasePresenter {
        val volcanoListObservable: LiveData<Result<List<Volcano>>>
        fun loadData()
    }

}