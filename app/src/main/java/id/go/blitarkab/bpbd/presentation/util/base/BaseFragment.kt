package id.go.blitarkab.bpbd.presentation.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.domain.model.common.*
import id.go.blitarkab.bpbd.presentation.util.extensions.showMessageDialog

abstract class BaseFragment<DataBindingClass : ViewDataBinding, PresenterClass : BasePresenter> :
    Fragment(), BaseView {

    protected lateinit var binding: DataBindingClass

    protected abstract val layoutResourceId: Int

    protected abstract val presenter: PresenterClass

    protected open fun onInitialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        onInitialize()

        return binding.root
    }

    override fun <T> getErrorMessage(result: Error<T>): String {
        return when (result) {
            is AuthError -> getString(R.string.msg_error_general_auth)
            is NetworkError -> getString(R.string.msg_error_general_network)
            is NotFoundError -> getString(R.string.msg_error_general_not_found)
            is InvalidDataError -> getString(R.string.msg_error_invalid_data, result.message)
            is GeneralError -> result.message
            is UnsupportedError -> result.message
        }
    }

    override fun <T> showErrorMessage(result: Error<T>, view: View) {
        showMessageDialog(getErrorMessage(result))
    }

    override fun showUnderDevelopmentMessage() {
        showMessageDialog(R.string.msg_under_development)
    }

    override fun closePage() {
        findNavController().navigateUp()
    }

}