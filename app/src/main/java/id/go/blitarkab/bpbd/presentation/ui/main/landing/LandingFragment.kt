package id.go.blitarkab.bpbd.presentation.ui.main.landing

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentLandingBinding
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class LandingFragment : BaseFragment<FragmentLandingBinding, LandingContract.Presenter>(),
    LandingContract.View {

    override val layoutResourceId: Int = R.layout.fragment_landing
    override val presenter: LandingContract.Presenter by viewModels<LandingViewModel>()

    private val pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            presenter.setContentPosition(position)
        }
    }

    private val indicatorAdapter = LandingContentIndicatorListAdapter()

    override fun onInitialize() {
        binding.view = this
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { onPrevPageClicked() }

        val pagerAdapter = LandingPagerAdapter(fragment = this)
        binding.pagerContent.apply {
            adapter = pagerAdapter
            registerOnPageChangeCallback(pagerCallback)
        }

        binding.listIndicator.adapter = indicatorAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.contentIndicatorListObservable.observe(viewLifecycleOwner) { fetchContentIndicatorList(it) }
    }

    override fun onDestroyView() {
        binding.pagerContent.unregisterOnPageChangeCallback(pagerCallback)
        super.onDestroyView()
    }

    override fun onPrevPageClicked() {
        val currentPosition = binding.pagerContent.currentItem
        if (currentPosition == 0) closePage()
        else binding.pagerContent.setCurrentItem(currentPosition - 1, true)
    }

    override fun onNextPageClicked() {
        val currentPosition = binding.pagerContent.currentItem
        if (currentPosition == 2) openLoginPage()
        else binding.pagerContent.setCurrentItem(currentPosition + 1, true)
    }

    override fun fetchContentIndicatorList(contentIndicators: List<LandingContract.ContentIndicator>) {
        indicatorAdapter.submitList(contentIndicators)
    }

    override fun openLoginPage() {
        val direction = LandingFragmentDirections.actionLandingFragmentToLoginFragment()
        findNavController().navigate(direction)
    }

    override fun closePage() {
        requireActivity().finish()
    }

}