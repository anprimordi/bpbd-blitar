package id.go.blitarkab.bpbd.presentation.ui.user.report.create.location

import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class UserReportCreateLocationViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), UserReportCreateLocationContract.Presenter {

}