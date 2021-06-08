package com.hhp227.recyclerviewstudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.recyclerviewstudy.R
import com.hhp227.recyclerviewstudy.model.ItemDto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.header.view.*
import kotlinx.android.synthetic.main.item.view.*

class ItemAdapter : ListAdapter<Any, RecyclerView.ViewHolder>(ItemDtoDiffCallback()) {
    private lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_HEADER ->
                return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header, parent, false))
            TYPE_CONTENT ->
                return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
        }
        throw IllegalStateException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(getItem(position).toString())
        } else if (holder is ItemViewHolder) {
            holder.bind(getItem(position) as ItemDto)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is String -> TYPE_HEADER
            is ItemDto -> TYPE_CONTENT
            else -> throw IllegalArgumentException()
        }
    }

    fun setOnItemClickListener(listener: (View, Int) -> Unit) {
        onItemClickListener = listener
    }

    inner class HeaderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(title: String) {
            containerView.tv_title.text = title
        }
    }

    inner class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.setOnClickListener {
                onItemClickListener(it, adapterPosition)
            }
        }

        fun bind(data: ItemDto) {
            containerView.name.text = data.name
        }
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }
}

private class ItemDtoDiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return false
    }
}
