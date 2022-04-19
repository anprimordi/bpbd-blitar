package id.go.blitarkab.bpbd.presentation.ui.user.report.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemReportUserBinding
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.presentation.util.comparator.ModelComparator

class UserReportListListAdapter(
    private val onItemClick: (Report) -> Unit
) : ListAdapter<Report, UserReportListListAdapter.ViewHolder>(ModelComparator<Report>()) {

    inner class ViewHolder(val binding: ItemReportUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_report_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
        holder.binding.textStatus.isVisible = false
        holder.binding.cardItem.setOnClickListener { onItemClick(item) }
    }

}