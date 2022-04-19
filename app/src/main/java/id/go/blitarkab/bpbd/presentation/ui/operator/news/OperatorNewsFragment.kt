package id.go.blitarkab.bpbd.presentation.ui.operator.news

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentOperatorNewsBinding
import id.go.blitarkab.bpbd.domain.model.News
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.ui.user.home.UserHomeNewsListAdapter
import id.go.blitarkab.bpbd.presentation.ui.user.news.list.UserNewsListFragmentDirections
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showLongSnackbar

@AndroidEntryPoint
class OperatorNewsFragment :
    BaseFragment<FragmentOperatorNewsBinding, OperatorNewsContract.Presenter>(),
    OperatorNewsContract.View {

    override val layoutResourceId: Int = R.layout.fragment_operator_news
    override val presenter: OperatorNewsContract.Presenter by viewModels<OperatorNewsViewModel>()

    private val listAdapter = UserHomeNewsListAdapter { openNewsDetail(it) }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listNews.adapter = listAdapter

        presenter.loadData()
        presenter.newsListObservable.observe(viewLifecycleOwner) { fetchNewsList(it) }
    }

    override fun fetchNewsList(result: Result<List<News>>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                val list = result.data
                binding.layoutMessageEmpty.isVisible = list.isEmpty()
                listAdapter.submitList(list)
            }
            is Error -> showLongSnackbar(binding.root, result.message)
        }
    }

    override fun openNewsDetail(news: News) {
        val direction = UserNewsListFragmentDirections.actionUserNewsListFragmentToUserNewsFragment(
            news = news
        )
        findNavController().navigate(direction)
    }

}