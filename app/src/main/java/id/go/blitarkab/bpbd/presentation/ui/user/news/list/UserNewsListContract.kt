package id.go.blitarkab.bpbd.presentation.ui.user.news.list

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.News
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserNewsListContract {

    interface View : BaseView {
        fun fetchNewsList(result: Result<List<News>>)
        fun openNewsDetail(news: News)
    }

    interface Presenter : BasePresenter {
        val newsListObservable: LiveData<Result<List<News>>>
        fun loadData()
    }

}