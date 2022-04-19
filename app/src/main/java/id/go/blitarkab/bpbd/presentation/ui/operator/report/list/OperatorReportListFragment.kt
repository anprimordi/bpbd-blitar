package id.go.blitarkab.bpbd.presentation.ui.operator.report.list

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentOperatorReportListBinding
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.ui.user.report.list.UserReportListListAdapter
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showSnackbar

@AndroidEntryPoint
class OperatorReportListFragment :
    BaseFragment<FragmentOperatorReportListBinding, OperatorReportListContract.Presenter>(),
    OperatorReportListContract.View {

    override val layoutResourceId: Int = R.layout.fragment_operator_report_list
    override val presenter: OperatorReportListContract.Presenter by viewModels<OperatorReportListViewModel>()

    private val reportListAdapter = UserReportListListAdapter { openReportDetailPage(it) }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listReport.adapter = reportListAdapter

        presenter.reportListObservable.observe(viewLifecycleOwner) {
            fetchReportList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
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

    override fun openReportDetailPage(report: Report) {
        val direction =
            OperatorReportListFragmentDirections.actionOperatorReportListFragmentToOperatorReportDetailFragment(
                reportId = report.id,
                readOnly = true
            )
        findNavController().navigate(direction)
    }

}