package id.go.blitarkab.bpbd.presentation.ui.operator.report.pager

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentOperatorReportPagerBinding
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.ui.operator.report.OperatorReportViewModel
import id.go.blitarkab.bpbd.presentation.ui.user.report.UserReportListAdapter
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showSnackbar

@AndroidEntryPoint
class OperatorReportPagerFragment :
    BaseFragment<FragmentOperatorReportPagerBinding, OperatorReportViewModel>(),
    OperatorReportPagerContract.View {

    override val layoutResourceId: Int = R.layout.fragment_operator_report_pager
    override val presenter: OperatorReportViewModel by activityViewModels()

    private val reportListAdapter = UserReportListAdapter {
        presenter.triggerEventOpenDetailReportPage(it)
    }

    private val isWaitingReport: Boolean by lazy {
        arguments?.getBoolean(OperatorReportPagerContract.ARG_IS_WAITING_REPORT, true) ?: true
    }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listReport.adapter = reportListAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isWaitingReport) {
            presenter.reportWaitingListObservable.observe(viewLifecycleOwner) { fetchReportList(it) }
        } else {
            presenter.reportFinishedListObservable.observe(viewLifecycleOwner) { fetchReportList(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun loadData() {
        if (isWaitingReport) {
            presenter.loadReportWaitingList()
        } else {
            presenter.loadReportFinishedList()
        }
    }

    override fun fetchReportList(result: Result<List<Report>>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                val list = result.data
                binding.layoutMessageEmpty.isVisible = list.isEmpty()
                binding.listReport.isVisible = list.isNotEmpty()
                reportListAdapter.submitList(list.sortedByDescending { it.datetime })
            }
            is Error -> showSnackbar(binding.root, result.message)
        }
    }

}