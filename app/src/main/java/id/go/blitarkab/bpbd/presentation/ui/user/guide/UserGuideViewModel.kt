package id.go.blitarkab.bpbd.presentation.ui.user.guide

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.presentation.ui.user.news.list.UserNewsListContract
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class UserGuideViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), UserGuideContract.Presenter {

}