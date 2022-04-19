package id.go.blitarkab.bpbd.presentation.ui.operator.news.detail

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.News
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface OperatorNewsDetailContract {

    interface View : BaseView {
        fun fetchNews(news: News)
        fun fetchNewsList(result: Result<List<News>>)
    }

    interface Presenter : BasePresenter {
        val newsListObservable: LiveData<Result<List<News>>>
        fun loadNewsList()
    }

}