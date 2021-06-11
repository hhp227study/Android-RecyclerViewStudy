package com.hhp227.recyclerviewstudy.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.*
import com.hhp227.recyclerviewstudy.R
import com.hhp227.recyclerviewstudy.activity.DetailActivity
import com.hhp227.recyclerviewstudy.model.HorizontalItemDto
import com.hhp227.recyclerviewstudy.model.ImageItemDto
import com.hhp227.recyclerviewstudy.model.SimpleItemDto
import com.hhp227.recyclerviewstudy.model.VerticalItemDto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.header.view.*
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import kotlinx.android.synthetic.main.item_simple.view.*
import java.util.*

class ItemAdapter : ListAdapter<Any, RecyclerView.ViewHolder>(ItemDtoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_HEADER ->
                return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header, parent, false))
            TYPE_IMAGE ->
                return ImageItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))
            TYPE_VERTICAL ->
                return VerticalItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false))
            TYPE_HORIZONTAL ->
                return HorizontalItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false))
        }
        throw IllegalStateException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(getItem(position).toString())
        } else if (holder is ImageItemViewHolder) {
            holder.bind(getItem(position) as ImageItemDto)
        } else if (holder is VerticalItemViewHolder) {
            holder.bind(getItem(position) as VerticalItemDto)
        } else if (holder is HorizontalItemViewHolder) {
            holder.bind(getItem(position) as HorizontalItemDto)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is String -> TYPE_HEADER
            is ImageItemDto -> TYPE_IMAGE
            is VerticalItemDto -> TYPE_VERTICAL
            is HorizontalItemDto -> TYPE_HORIZONTAL
            else -> throw IllegalArgumentException()
        }
    }

    inner class HeaderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(title: String) {
            containerView.tv_title.text = title
        }
    }

    inner class VerticalItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(data: VerticalItemDto) {
            containerView.recycler_view.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = VerticalItemAdapter(data.list).apply {
                    setOnItemClickListener { _, i ->
                        if (currentList[i] is SimpleItemDto) {
                            val item = currentList[i] as SimpleItemDto
                            val intent = Intent(context, DetailActivity::class.java).putExtra("id", item.id)
                                .putExtra("pos", i)
                                .putExtra("name", item.name)

                            startActivity(context, intent, null)
                        }
                    }
                }
            }.also(ItemTouchHelper(ItemTouchCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT))::attachToRecyclerView)
        }

        inner class ItemTouchCallback(dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return viewHolder is VerticalItemAdapter.SimpleViewHolder
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewHolder.adapterPosition.also { position ->
                    (containerView.recycler_view.adapter as VerticalItemAdapter).mutableList.removeAt(position)

                    containerView.recycler_view.adapter?.notifyItemRemoved(position)
                }
            }

            override fun onMoved(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, fromPos: Int, target: RecyclerView.ViewHolder, toPos: Int, x: Int, y: Int) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
                Collections.swap((recyclerView.adapter as VerticalItemAdapter).mutableList, fromPos, toPos)
                recyclerView.adapter?.notifyItemMoved(fromPos, toPos)
            }
        }
    }

    inner class ImageItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(data: ImageItemDto) {
            containerView.image_name.text = data.title

            containerView.image_view.setImageResource(data.resId)
        }
    }

    inner class HorizontalItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(data: HorizontalItemDto) {
            containerView.recycler_view.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = HorizontalItemAdapter().apply {
                    submitList(data.list)
                }
            }
        }
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_IMAGE = 1
        private const val TYPE_VERTICAL = 2
        private const val TYPE_HORIZONTAL = 3
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
