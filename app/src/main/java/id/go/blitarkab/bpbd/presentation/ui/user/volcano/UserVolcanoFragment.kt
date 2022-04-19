package id.go.blitarkab.bpbd.presentation.ui.user.volcano

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserVolcanoBinding
import id.go.blitarkab.bpbd.domain.model.Volcano
import id.go.blitarkab.bpbd.domain.model.common.*
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showLongSnackbar

@AndroidEntryPoint
class UserVolcanoFragment :
    BaseFragment<FragmentUserVolcanoBinding, UserVolcanoContract.Presenter>(),
    UserVolcanoContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_volcano
    override val presenter: UserVolcanoContract.Presenter by viewModels<UserVolcanoViewModel>()

    private val volcanoListAdapter = UserVolcanoListAdapter()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listVolcano.adapter = volcanoListAdapter

        presenter.loadData()
        presenter.volcanoListObservable.observe(viewLifecycleOwner) { fetchVolcanoList(it) }
    }

    override fun fetchVolcanoList(result: Result<List<Volcano>>) {
        when(result) {
            is Loading -> {}
            is Success -> {
                val list = result.data
                volcanoListAdapter.submitList(list)
                binding.layoutMessageEmpty.isVisible = list.isEmpty()
            }
            is Error -> showLongSnackbar(binding.root, result.message)
        }
    }

}