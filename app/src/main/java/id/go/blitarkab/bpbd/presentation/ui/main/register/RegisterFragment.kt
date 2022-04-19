package id.go.blitarkab.bpbd.presentation.ui.main.register

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentRegisterBinding
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.ui.user.UserActivity
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showLongToast
import id.go.blitarkab.bpbd.presentation.util.extensions.showMessageDialog

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterContract.Presenter>(),
    RegisterContract.View {

    override val layoutResourceId: Int = R.layout.fragment_register
    override val presenter: RegisterContract.Presenter by viewModels<RegisterViewModel>()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter

        binding.choiceGender.apply {
            threshold = 1000
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_drop_down_menu,
                    resources.getStringArray(R.array.gender_list)
                )
            )
        }

        presenter.eventRegisterObservable.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { onRegisterFinished(it) }
        }
    }

    override fun onRegisterFinished(result: Result<Account>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                val account = result.data
                showLongToast(getString(R.string.msg_success_register, account.fullName))
                openUserPage()
            }
            is Error -> {
                val errorMessage = getErrorMessage(result)
                showMessageDialog(errorMessage)
            }
        }
    }

    override fun openLoginPage() {
        findNavController().navigateUp()
    }

    override fun openUserPage() {
        val intent = UserActivity.createIntent(requireContext())
        startActivity(intent)
        requireActivity().finish()
    }

}