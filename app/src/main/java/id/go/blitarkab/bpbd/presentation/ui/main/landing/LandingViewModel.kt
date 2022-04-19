package id.go.blitarkab.bpbd.presentation.ui.main.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import id.go.blitarkab.bpbd.presentation.util.extensions.freshInsert
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), LandingContract.Presenter {

    private val landingContentList = listOf(
        LandingContract.Content(
            titleRes = R.string.msg_landing_content_title_1,
            messageRes = R.string.msg_landing_content_message_1,
            imageRes = R.drawable.img_landing_content_1
        ),
        LandingContract.Content(
            titleRes = R.string.msg_landing_content_title_2,
            messageRes = R.string.msg_landing_content_message_2,
            imageRes = R.drawable.img_landing_content_2
        ),
        LandingContract.Content(
            titleRes = R.string.msg_landing_content_title_3,
            messageRes = R.string.msg_landing_content_message_3,
            imageRes = R.drawable.img_landing_content_3
        )
    )

    private val contentIndicators = arrayListOf<LandingContract.ContentIndicator>()
    private val _contentIndicatorListObservable = MutableLiveData<List<LandingContract.ContentIndicator>>()
    override val contentIndicatorListObservable: LiveData<List<LandingContract.ContentIndicator>>
        get() = _contentIndicatorListObservable

    init {
        contentIndicators.addAll(
            listOf(
                LandingContract.ContentIndicator(position = 0, isSelected = true),
                LandingContract.ContentIndicator(position = 1, isSelected = false),
                LandingContract.ContentIndicator(position = 2, isSelected = false),
            )
        )
        _contentIndicatorListObservable.value = contentIndicators
    }

    override fun setContentPosition(position: Int) {
        val list = arrayListOf<LandingContract.ContentIndicator>()
        for (item in contentIndicators) {
            if (item.position == position) {
                list.add(LandingContract.ContentIndicator(position = position, isSelected = true))
            } else {
                list.add(item.copy(isSelected = false))
            }
        }
        _contentIndicatorListObservable.postValue(list)
        contentIndicators.freshInsert(list)
    }

    override fun getContentObservable(position: Int): LiveData<LandingContract.Content> {
        return liveData {
            val content = landingContentList.getOrNull(position)
            if (content != null) emit(content)
        }
    }

}