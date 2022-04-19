package id.go.blitarkab.bpbd.presentation.ui.user.guide.detail

import android.os.Build
import android.text.Html
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserGuideDetailBinding
import id.go.blitarkab.bpbd.domain.model.Guide
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class UserGuideDetailFragment :
    BaseFragment<FragmentUserGuideDetailBinding, UserGuideDetailContract.Presenter>(),
    UserGuideDetailContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_guide_detail
    override val presenter: UserGuideDetailContract.Presenter by viewModels<UserGuideDetailViewModel>()

    private val safeArgs: UserGuideDetailFragmentArgs by navArgs()
    private val guide: Guide by lazy { safeArgs.guide }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.textToolbarTitle.text = guide.title
        binding.imageGuide.setImageResource(guide.imageRes)
        binding.textContent.text =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(guide.content, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(guide.content)
            }
    }

}