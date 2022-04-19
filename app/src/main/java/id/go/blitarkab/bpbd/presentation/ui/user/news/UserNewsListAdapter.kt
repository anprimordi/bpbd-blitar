package id.go.blitarkab.bpbd.presentation.ui.user.news

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemNewsHorizontalBinding
import id.go.blitarkab.bpbd.domain.model.News
import id.go.blitarkab.bpbd.presentation.util.comparator.ModelComparator

class UserNewsListAdapter(
    private val onItemClick: (News) -> Unit
) : ListAdapter<News, UserNewsListAdapter.ViewHolder>(ModelComparator<News>()) {

    inner class ViewHolder(val binding: ItemNewsHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news_horizontal,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
        holder.binding.textContent.text =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(item.content, Html.FROM_HTML_MODE_LEGACY);
            } else {
                Html.fromHtml(item.content)
            }
        holder.binding.cardItem.setOnClickListener { onItemClick(item) }
    }

}