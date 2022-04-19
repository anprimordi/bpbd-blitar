package id.go.blitarkab.bpbd.presentation.ui.operator.report

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.go.blitarkab.bpbd.presentation.ui.operator.report.pager.OperatorReportPagerContract
import id.go.blitarkab.bpbd.presentation.ui.operator.report.pager.OperatorReportPagerFragment

class OperatorReportPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = OperatorReportPagerFragment()
        fragment.arguments = bundleOf(
            Pair(OperatorReportPagerContract.ARG_IS_WAITING_REPORT, position == 0)
        )
        return fragment
    }
}