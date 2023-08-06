package com.faanghut.reflection.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.faanghut.reflection.databinding.ItemPageBinding
import com.faanghut.reflection.databinding.ItemPageDateBinding
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.models.PageDateWithPages
import com.faanghut.reflection.utils.getDateString

class PageDateAdapter: ListAdapter<PageDateWithPages, PageDateAdapter.ViewH>(PageDateWithPagesDiffCallback()) {

    var pageDateWithPagesList: List<PageDateWithPages> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageDateAdapter.ViewH {
        val binding = ItemPageDateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewH(binding)
    }

    override fun onBindViewHolder(holder: PageDateAdapter.ViewH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return pageDateWithPagesList.size
    }

    fun updatePageDateWithPages(pageDateWithPagesList: List<PageDateWithPages>) {
        this.pageDateWithPagesList = pageDateWithPagesList
        notifyDataSetChanged()
    }

    inner class ViewH(private val binding: ItemPageDateBinding) : ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val pageDateWithPage = pageDateWithPagesList[position]

                tvDate.text = pageDateWithPage.pageDate.date.getDateString()
                val pageAdapter = PageAdapter()
                rvPages.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = pageAdapter
                }
                pageAdapter.updatePages(pageDateWithPage.pages)
            }
        }
    }
}

class PageDateWithPagesDiffCallback : DiffUtil.ItemCallback<PageDateWithPages>() {
    override fun areItemsTheSame(oldItem: PageDateWithPages, newItem: PageDateWithPages): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PageDateWithPages, newItem: PageDateWithPages): Boolean {
        return oldItem == newItem
    }
}