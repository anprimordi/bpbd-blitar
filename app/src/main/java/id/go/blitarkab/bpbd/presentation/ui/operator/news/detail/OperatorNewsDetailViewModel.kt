package id.go.blitarkab.bpbd.presentation.ui.operator.news.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.DashboardDataSource
import id.go.blitarkab.bpbd.domain.model.News
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.ui.user.news.list.UserNewsListContract
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperatorNewsDetailViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val dashboardDataSource: DashboardDataSource
) : BaseViewModel(accountDataSource), OperatorNewsDetailContract.Presenter {

    private val _newsListObservable = MutableLiveData<Result<List<News>>>()
    override val newsListObservable: LiveData<Result<List<News>>>
        get() = _newsListObservable

    override fun loadNewsList() {
        viewModelScope.launch {
            setLoading()
            val result = dashboardDataSource.getNewsList()
            _newsListObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

}