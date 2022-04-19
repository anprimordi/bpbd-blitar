package id.go.blitarkab.bpbd.presentation.ui.user.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemReportHorizontalBinding
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.presentation.util.comparator.ModelComparator

class UserHomeReportListAdapter(
    private val onItemClick: (Report) -> Unit
) : ListAdapter<Report, UserHomeReportListAdapter.ViewHolder>(ModelComparator<Report>()) {

    inner class ViewHolder(val binding: ItemReportHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_report_horizontal,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
        holder.binding.cardItem.setOnClickListener { onItemClick(item) }
    }

}