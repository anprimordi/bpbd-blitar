package id.go.blitarkab.bpbd.presentation.ui.operator.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.ReportFragment
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface OperatorReportContract {

    interface View : BaseView {
        fun setupPager()
        fun showPagerAt(index: Int)
        fun openReportDetailPage(report: Report)
    }

    interface Presenter : BasePresenter {
        val reportWaitingListObservable: LiveData<Result<List<Report>>>
        val reportFinishedListObservable: LiveData<Result<List<Report>>>

        val eventOpenDetailReportPageObservable: LiveData<Event<Report>>

        fun loadReportWaitingList()
        fun loadReportFinishedList()
        fun triggerEventOpenDetailReportPage(report: Report)
    }

}