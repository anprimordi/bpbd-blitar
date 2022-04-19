package id.go.blitarkab.bpbd.presentation.ui.operator.profile.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentOperatorProfileDetailBinding
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showLongSnackbar

@AndroidEntryPoint
class OperatorProfileDetailFragment :
    BaseFragment<FragmentOperatorProfileDetailBinding, OperatorProfileDetailContract.Presenter>(),
    OperatorProfileDetailContract.View {

    override val layoutResourceId: Int = R.layout.fragment_operator_profile_detail
    override val presenter: OperatorProfileDetailContract.Presenter by viewModels<OperatorProfileDetailViewModel>()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter

        presenter.accountObservable.observe(viewLifecycleOwner) { fetchAccount(it) }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
    }

    override fun fetchAccount(result: Result<Account>) {
        when (result) {
            is Loading -> {
            }
            is Success -> binding.account = result.data
            is Error -> showLongSnackbar(binding.root, result.message)
        }
    }

    override fun openProfileUpdatePage() {
        val direction = OperatorProfileDetailFragmentDirections.actionOperatorProfileDetailFragmentToOperatorProfileUpdateFragment()
        findNavController().navigate(direction)
    }

}