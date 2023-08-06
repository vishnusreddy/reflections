package com.faanghut.reflection.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.faanghut.reflection.databinding.ItemPageBinding
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.utils.to12HourFormat

class PageAdapter : ListAdapter<Page, PageAdapter.ViewH>(PageDiffCallback()) {

    var pages: List<Page> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageAdapter.ViewH {
        val binding = ItemPageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewH(binding)
    }

    override fun onBindViewHolder(holder: PageAdapter.ViewH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    inner class ViewH(val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val page = pages[position]
                tvTime.text = page.lastEditedTimestamp.to12HourFormat()
                tvTitle.text = page.title
                tvBody.text = page.body
            }
        }
    }

    fun updatePages(pages: List<Page>) {
        this.pages = pages
        notifyDataSetChanged()
    }

}

class PageDiffCallback : DiffUtil.ItemCallback<Page>() {
    override fun areItemsTheSame(oldItem: Page, newItem: Page): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Page, newItem: Page): Boolean {
        return oldItem == newItem
    }
}