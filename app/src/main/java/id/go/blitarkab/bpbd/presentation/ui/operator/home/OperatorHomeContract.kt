package id.go.blitarkab.bpbd.presentation.ui.operator.home

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.*
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface OperatorHomeContract {

    interface View : BaseView {
        fun fetchAccount(result: Result<Account>)
        fun fetchDashboardInfo(result: Result<Dashboard>)
        fun fetchReportList(result: Result<List<Report>>)
        fun fetchWeatherList(result: Result<List<Weather>>)

        fun onWeatherSelected(weather: Weather)

        fun openProfileDetailPage()
        fun openVolcanoPage()
        fun openCovidPage()
        fun openEarthQuakePage()
        fun openSatellitePage()
        fun openReportListPage()
        fun openReportDetailPage(report: Report)
        fun openNewsPage(news: News)
    }

    interface Presenter : BasePresenter {
        val todayObservable: LiveData<String>
        val accountObservable: LiveData<Result<Account>>
        val dashboardInfoObservable: LiveData<Result<Dashboard>>
        val reportListObservable: LiveData<Result<List<Report>>>
        val weatherListObservable: LiveData<Result<List<Weather>>>

        fun loadData()
    }

}