package id.go.blitarkab.bpbd.presentation.ui.user.guide

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserGuideBinding
import id.go.blitarkab.bpbd.domain.model.Guide
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class UserGuideFragment :
    BaseFragment<FragmentUserGuideBinding, UserGuideContract.Presenter>(),
    UserGuideContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_guide
    override val presenter: UserGuideContract.Presenter by viewModels<UserGuideViewModel>()

    private val listAdapter = UserGuideListAdapter {
        val direction =
            UserGuideFragmentDirections.actionUserGuideFragmentToUserGuideDetailFragment(it)
        findNavController().navigate(direction)
    }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listGuide.adapter = listAdapter
        listAdapter.submitList(Guide.ALL_VALUES)
    }

}