package id.go.blitarkab.bpbd.presentation.ui.user.report.list

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserReportListBinding
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showSnackbar

@AndroidEntryPoint
class UserReportListFragment :
    BaseFragment<FragmentUserReportListBinding, UserReportListContract.Presenter>(),
    UserReportListContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_report_list
    override val presenter: UserReportListContract.Presenter by viewModels<UserReportListViewModel>()

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
            UserReportListFragmentDirections.actionUserReportListFragmentToUserReportDetailFragment(
                reportId = report.id,
                isHistory = false
            )
        findNavController().navigate(direction)
    }

}