package com.hhp227.recyclerviewstudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.recyclerviewstudy.R
import com.hhp227.recyclerviewstudy.model.SimpleItemDto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import kotlinx.android.synthetic.main.item_simple.view.*
import java.util.*


class VerticalItemAdapter(val mutableList: MutableList<SimpleItemDto>) : RecyclerView.Adapter<VerticalItemAdapter.SimpleViewHolder>() {
    private lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_simple, parent, false))
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = mutableList.size

    private fun getItem(position: Int) = mutableList[position]

    fun setOnItemClickListener(listener: (View, Int) -> Unit) {
        onItemClickListener = listener
    }

    inner class SimpleViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.setOnClickListener {
                onItemClickListener(it, adapterPosition)
            }
        }

        fun bind(item: SimpleItemDto) {
            containerView.name.text = item.name
        }
    }
}

private class SimpleDiffCallback : DiffUtil.ItemCallback<SimpleItemDto>() {
    override fun areItemsTheSame(oldItem: SimpleItemDto, newItem: SimpleItemDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SimpleItemDto, newItem: SimpleItemDto): Boolean {
        return false
    }
}
