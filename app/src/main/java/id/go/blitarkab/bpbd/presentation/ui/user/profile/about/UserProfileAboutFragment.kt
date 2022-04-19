package id.go.blitarkab.bpbd.presentation.ui.user.profile.about

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserProfileAboutBinding
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class UserProfileAboutFragment :
    BaseFragment<FragmentUserProfileAboutBinding, UserProfileAboutContract.Presenter>(),
    UserProfileAboutContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_profile_about
    override val presenter: UserProfileAboutContract.Presenter by viewModels<UserProfileAboutViewModel>()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
    }

}