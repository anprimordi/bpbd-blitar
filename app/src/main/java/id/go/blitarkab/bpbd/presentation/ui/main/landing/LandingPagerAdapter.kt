package id.go.blitarkab.bpbd.presentation.ui.main.landing

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.go.blitarkab.bpbd.presentation.ui.main.landing.LandingContract.Companion.ARG_CONTENT_POSITION

class LandingPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return LandingContentFragment().apply {
            arguments = bundleOf(Pair(ARG_CONTENT_POSITION, position))
        }
    }

}