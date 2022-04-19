package id.go.blitarkab.bpbd.presentation.ui.operator.report

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentOperatorReportBinding
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.colorAttrOf

@AndroidEntryPoint
class OperatorReportFragment :
    BaseFragment<FragmentOperatorReportBinding, OperatorReportViewModel>(),
    OperatorReportContract.View {

    override val layoutResourceId: Int = R.layout.fragment_operator_report
    override val presenter: OperatorReportViewModel by activityViewModels()

    private val pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when (position) {
                0 -> {
                    binding.textTabWaiting.apply {
                        setTextColor(colorAttrOf(R.attr.colorOnSecondary))
                        setBackgroundResource(R.drawable.gradient_operator)
                    }
                    binding.textTabFinished.apply {
                        setTextColor(colorAttrOf(R.attr.colorHint))
                        setBackgroundColor(colorAttrOf(R.attr.colorFormBackground))
                    }
                }
                1 -> {
                    binding.textTabWaiting.apply {
                        setTextColor(colorAttrOf(R.attr.colorHint))
                        setBackgroundColor(colorAttrOf(R.attr.colorFormBackground))
                    }
                    binding.textTabFinished.apply {
                        setTextColor(colorAttrOf(R.attr.colorOnSecondary))
                        setBackgroundResource(R.drawable.gradient_operator)
                    }
                }
            }
        }
    }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        setupPager()

        presenter.eventOpenDetailReportPageObservable.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { openReportDetailPage(it) }
        }
    }

    override fun onDestroyView() {
        binding.pagerReport.unregisterOnPageChangeCallback(pagerCallback)
        super.onDestroyView()
    }

    override fun setupPager() {
        val adapter = OperatorReportPagerAdapter(fragment = this)
        binding.pagerReport.apply {
            setAdapter(adapter)
            registerOnPageChangeCallback(pagerCallback)
        }
    }

    override fun showPagerAt(index: Int) {
        binding.pagerReport.setCurrentItem(index, true)
    }

    override fun openReportDetailPage(report: Report) {
        val direction =
            OperatorReportFragmentDirections.actionOperatorReportFragmentToOperatorReportDetailFragment(
                reportId = report.id,
                readOnly = false
            )
        findNavController().navigate(direction)
    }

}