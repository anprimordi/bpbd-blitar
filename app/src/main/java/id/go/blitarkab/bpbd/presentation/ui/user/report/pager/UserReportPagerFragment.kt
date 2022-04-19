package id.go.blitarkab.bpbd.presentation.ui.user.report.pager

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserReportPagerBinding
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class UserReportPagerFragment :
    BaseFragment<FragmentUserReportPagerBinding, UserReportPagerContract.Presenter>(),
    UserReportPagerContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_report_pager
    override val presenter: UserReportPagerContract.Presenter by viewModels<UserReportPagerViewModel>()

    override fun onInitialize() {

    }

}