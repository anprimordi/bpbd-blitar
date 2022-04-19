package id.go.blitarkab.bpbd.presentation.ui.main.login

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentLoginBinding
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.ui.operator.OperatorActivity
import id.go.blitarkab.bpbd.presentation.ui.user.UserActivity
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showMessageDialog

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginContract.Presenter>(),
    LoginContract.View {

    override val layoutResourceId: Int = R.layout.fragment_login
    override val presenter: LoginContract.Presenter by viewModels<LoginViewModel>()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { closePage() }

        presenter.eventLoginObservable.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { onLoginFinished(it) }
        }
    }

    override fun onLoginFinished(result: Result<Account>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                val account = result.data
                when (account.type) {
                    Account.Type.USER -> openUserPage()
                    Account.Type.OPERATOR -> openOperatorPage()
                }
            }
            is Error -> {
                val errorMessage = getErrorMessage(result)
                showMessageDialog(errorMessage)
            }
        }
    }

    override fun openForgotPasswordPage() {
        val direction = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
        findNavController().navigate(direction)
    }

    override fun openRegisterPage() {
        val direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(direction)
    }

    override fun openUserPage() {
        val intent = UserActivity.createIntent(requireContext())
        startActivity(intent)
        closePage()
    }

    override fun openOperatorPage() {
        val intent = OperatorActivity.createIntent(requireContext())
        startActivity(intent)
        closePage()
    }

    override fun closePage() {
        requireActivity().finish()
    }

}