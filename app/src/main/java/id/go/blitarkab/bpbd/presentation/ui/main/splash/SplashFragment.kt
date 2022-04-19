package id.go.blitarkab.bpbd.presentation.ui.main.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentSplashBinding
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.presentation.ui.operator.OperatorActivity
import id.go.blitarkab.bpbd.presentation.ui.user.UserActivity
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashContract.Presenter>(),
    SplashContract.View {

    override val layoutResourceId: Int = R.layout.fragment_splash
    override val presenter: SplashContract.Presenter by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadAccount()
        presenter.accountStateObservable.observe(viewLifecycleOwner) { fetchAccountState(it) }
    }

    override fun fetchAccountState(account: Account?) {
        if (account != null) {
            when (account.type) {
                Account.Type.USER -> openUserPage()
                Account.Type.OPERATOR -> openOperatorPage()
            }
        } else {
            openLandingPage()
        }
    }

    override fun openLandingPage() {
        val direction = SplashFragmentDirections.actionSplashFragmentToLandingFragment()
        findNavController().navigate(direction)
    }

    override fun openUserPage() {
        val intent = UserActivity.createIntent(requireContext())
        startActivity(intent)
        requireActivity().finish()
    }

    override fun openOperatorPage() {
        val intent = OperatorActivity.createIntent(requireContext())
        startActivity(intent)
        requireActivity().finish()
    }

}