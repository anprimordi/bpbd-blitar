package id.go.blitarkab.bpbd.presentation.ui.main.forgotpassword

import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentForgotPasswordBinding
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showMessageDialog

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding, ForgotPasswordContract.Presenter>(),
    ForgotPasswordContract.View {

    override val layoutResourceId: Int = R.layout.fragment_forgot_password
    override val presenter: ForgotPasswordContract.Presenter by viewModels<ForgotPasswordViewModel>()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter

        presenter.eventForgotPasswordObservable.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { onForgotPasswordFinished(it) }
        }
    }

    override fun onForgotPasswordFinished(result: Result<Unit>) {
        when (result) {
            is Loading -> {
            }
            is Success -> showForgotPasswordSuccessDialog()
            is Error -> {
                val errorMessage = getErrorMessage(result)
                showMessageDialog(errorMessage)
            }
        }
    }

    override fun showForgotPasswordSuccessDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.msg_title_forgot_password_success)
            .setMessage(R.string.msg_success_forgot_password)
            .setCancelable(false)
            .setPositiveButton(R.string.action_ok) { dialog, _ ->
                dialog.dismiss()
                closePage()
            }
            .show()
    }

}