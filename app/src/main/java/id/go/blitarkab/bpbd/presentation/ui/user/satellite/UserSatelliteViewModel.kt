package id.go.blitarkab.bpbd.presentation.ui.user.satellite

import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class UserSatelliteViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), UserSatelliteContract.Presenter {

}