package id.go.blitarkab.bpbd.presentation.ui.user.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemReportUserBinding
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.presentation.util.comparator.ModelComparator
import id.go.blitarkab.bpbd.presentation.util.extensions.colorAttrOf
import id.go.blitarkab.bpbd.presentation.util.extensions.getDisplayColorRes
import id.go.blitarkab.bpbd.presentation.util.extensions.getDisplayNameRes

class UserReportListAdapter(
    private val onItemClick: (Report) -> Unit
) : ListAdapter<Report, UserReportListAdapter.ViewHolder>(ModelComparator<Report>()) {

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
        val context = holder.binding.root.context
        val item = getItem(position)
        holder.binding.item = item
        holder.binding.textStatus.apply {
            setText(item.status.getDisplayNameRes())
            setTextColor(context.colorAttrOf(item.status.getDisplayColorRes()))
        }
        holder.binding.cardItem.setOnClickListener { onItemClick(item) }
    }

}