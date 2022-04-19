package id.go.blitarkab.bpbd.presentation.ui.operator.report.pager

import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface OperatorReportPagerContract {

    companion object {
        const val ARG_IS_WAITING_REPORT: String = "arguments_key_is_waiting_report"
    }

    interface View : BaseView {
        fun loadData()
        fun fetchReportList(result: Result<List<Report>>)
    }

    interface Presenter : BasePresenter {

    }

}