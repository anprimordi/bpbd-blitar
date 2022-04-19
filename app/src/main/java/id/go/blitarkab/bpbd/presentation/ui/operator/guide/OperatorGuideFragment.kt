package id.go.blitarkab.bpbd.presentation.ui.operator.guide

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentOperatorGuideBinding
import id.go.blitarkab.bpbd.domain.model.Guide
import id.go.blitarkab.bpbd.presentation.ui.user.guide.UserGuideFragmentDirections
import id.go.blitarkab.bpbd.presentation.ui.user.guide.UserGuideListAdapter
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class OperatorGuideFragment :
    BaseFragment<FragmentOperatorGuideBinding, OperatorGuideContract.Presenter>(),
    OperatorGuideContract.View {

    override val layoutResourceId: Int = R.layout.fragment_operator_guide
    override val presenter: OperatorGuideContract.Presenter by viewModels<OperatorGuideViewModel>()

    private val listAdapter = UserGuideListAdapter {
        val direction =
            OperatorGuideFragmentDirections.actionOperatorGuideFragmentToUserGuideDetailFragment2(it)
        findNavController().navigate(direction)
    }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listGuide.adapter = listAdapter
        listAdapter.submitList(Guide.ALL_VALUES)
    }

}