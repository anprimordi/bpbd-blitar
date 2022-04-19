package id.go.blitarkab.bpbd.presentation.ui.main.landing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentLandingContentBinding
import id.go.blitarkab.bpbd.presentation.ui.main.landing.LandingContract.Companion.ARG_CONTENT_POSITION
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class LandingContentFragment :
    BaseFragment<FragmentLandingContentBinding, LandingContract.Presenter>(),
    LandingContract.ViewContent {

    override val layoutResourceId: Int = R.layout.fragment_landing_content
    override val presenter: LandingContract.Presenter by activityViewModels<LandingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ARG_CONTENT_POSITION) }?.apply {
            val position = getInt(ARG_CONTENT_POSITION)
            presenter.getContentObservable(position).observe(viewLifecycleOwner) {
                fetchContent(it)
            }
        }
    }

    override fun fetchContent(content: LandingContract.Content) {
        binding.textTitle.setText(content.titleRes)
        binding.textMessage.setText(content.messageRes)
        Glide.with(requireContext()).load(content.imageRes).into(binding.imageContent)
    }

}