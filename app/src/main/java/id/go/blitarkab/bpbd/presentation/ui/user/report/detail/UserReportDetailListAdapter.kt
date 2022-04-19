package id.go.blitarkab.bpbd.presentation.ui.user.report.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemImageReportDetailBinding
import id.go.blitarkab.bpbd.presentation.util.comparator.SimpleComparator

class UserReportDetailListAdapter(
    private val onImageClicked: (Int) -> Unit
) : ListAdapter<String, UserReportDetailListAdapter.ViewHolder>(SimpleComparator<String>()) {

    inner class ViewHolder(val binding: ItemImageReportDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image_report_detail,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
        holder.binding.imageReport.setOnClickListener { onImageClicked(position) }
    }

}