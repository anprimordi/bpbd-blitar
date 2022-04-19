package id.go.blitarkab.bpbd.presentation.ui.user.news

import android.os.Build
import android.text.Html
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserNewsBinding
import id.go.blitarkab.bpbd.domain.model.News
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class UserNewsFragment :
    BaseFragment<FragmentUserNewsBinding, UserNewsContract.Presenter>(),
    UserNewsContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_news
    override val presenter: UserNewsContract.Presenter by viewModels<UserNewsViewModel>()

    private val safeArgs: UserNewsFragmentArgs by navArgs()
    private val newsListAdapter = UserNewsListAdapter { fetchNews(it) }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listNews.adapter = newsListAdapter

        fetchNews(safeArgs.news)
        presenter.loadNewsList()
        presenter.newsListObservable.observe(viewLifecycleOwner) { fetchNewsList(it) }
    }

    override fun fetchNews(news: News) {
        binding.news = news
        binding.textContent.text =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(news.content, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(news.content)
            }
    }

    override fun fetchNewsList(result: Result<List<News>>) {
        if (result is Success) {
            val newsList = result.data
            binding.textOtherNews.isVisible = newsList.isNotEmpty()
            binding.listNews.isVisible = newsList.isNotEmpty()
            newsListAdapter.submitList(newsList)
        } else {
            binding.textOtherNews.isVisible = false
            binding.listNews.isVisible = false
        }
    }

}