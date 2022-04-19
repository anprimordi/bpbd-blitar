package id.go.blitarkab.bpbd.presentation.ui.user.report.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemImageReportBinding
import id.go.blitarkab.bpbd.presentation.util.comparator.SimpleComparator
import id.go.blitarkab.bpbd.presentation.util.extensions.setImageLocalSource

class UserReportCreateImageListAdapter(
    private val onImageClicked: (Int) -> Unit,
    private val onActionRemoveClicked: (UserReportCreateContract.ReportImage) -> Unit,
) : ListAdapter<UserReportCreateContract.ReportImage, UserReportCreateImageListAdapter.ViewHolder>(
    SimpleComparator<UserReportCreateContract.ReportImage>()
) {

    inner class ViewHolder(val binding: ItemImageReportBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image_report,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = getItem(position)
        holder.binding.imageActionRemove.setOnClickListener { onActionRemoveClicked(image) }
        holder.binding.imageReport.setOnClickListener { onImageClicked(position) }
        holder.binding.imageReport.setImageLocalSource(
            srcLocal = image.content,
            srcPlaceholderRes = R.drawable.img_bpbd_40,
            srcErrorRes = R.drawable.img_bpbd_40,
            roundedRadius = 8f
        )
    }

}