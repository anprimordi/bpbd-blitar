package id.go.blitarkab.bpbd.presentation.ui.operator.profile

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.BuildConfig
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentOperatorProfileBinding
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.ui.main.MainActivity
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.setImageRemoteSource
import id.go.blitarkab.bpbd.presentation.util.extensions.showConfirmationDialog
import id.go.blitarkab.bpbd.presentation.util.extensions.showLongSnackbar
import id.go.blitarkab.bpbd.presentation.util.extensions.showMessageDialog

@AndroidEntryPoint
class OperatorProfileFragment :
    BaseFragment<FragmentOperatorProfileBinding, OperatorProfileContract.Presenter>(),
    OperatorProfileContract.View {

    override val layoutResourceId: Int = R.layout.fragment_operator_profile
    override val presenter: OperatorProfileContract.Presenter by viewModels<OperatorProfileViewModel>()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter

        presenter.loadData()
        presenter.accountObservable.observe(viewLifecycleOwner) { fetchAccount(it) }
        presenter.eventLogoutObservable.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { onLogoutFinished(it) }
        }
    }

    override fun fetchAccount(result: Result<Account>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                val account = result.data
                binding.textName.text = account.fullName
                binding.textEmail.text = account.email
                binding.imageProfile.setImageRemoteSource(
                    srcRemote = account.photoProfileUrl,
                    srcPlaceholderRes = R.drawable.ic_placeholder_account_48,
                    srcErrorRes = R.drawable.ic_placeholder_account_48,
                    roundedRadius = 8f
                )
            }
            is Error -> showLongSnackbar(binding.root, result.message)
        }
    }

    override fun onLogoutFinished(result: Result<Unit>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                showLongSnackbar(binding.root, R.string.msg_success_logout)
                openLoginPage()
            }
            is Error -> showMessageDialog(result.message)
        }
    }

    override fun showLogoutConfirmationDialog() {
        showConfirmationDialog(R.string.msg_confirmation_logout) { presenter.logout() }
    }

    override fun openLoginPage() {
        val intent = MainActivity.createIntent(requireContext())
        startActivity(intent)
        requireActivity().finish()
    }

    override fun openProfileDetailPage() {
        val direction =
            OperatorProfileFragmentDirections.actionOperatorProfileFragmentToOperatorProfileDetailFragment()
        findNavController().navigate(direction)
    }

    override fun openReviewAppPage() {
        val appPackageName = BuildConfig.APPLICATION_ID
        val url = "https://play.google.com/store/apps/details?id=$appPackageName"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    override fun openAboutPage() {
        val direction =
            OperatorProfileFragmentDirections.actionOperatorProfileFragmentToUserProfileAboutFragment2()
        findNavController().navigate(direction)
    }

}