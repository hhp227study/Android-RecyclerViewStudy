package com.hhp227.recyclerviewstudy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.recyclerviewstudy.R
import com.hhp227.recyclerviewstudy.adapter.ItemAdapter
import com.hhp227.recyclerviewstudy.adapter.VerticalItemAdapter
import com.hhp227.recyclerviewstudy.model.HorizontalItemDto
import com.hhp227.recyclerviewstudy.model.ImageItemDto
import com.hhp227.recyclerviewstudy.model.SimpleItemDto
import com.hhp227.recyclerviewstudy.model.VerticalItemDto
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val dataList: ArrayList<*> = arrayListOf(
        "섹션1",
        VerticalItemDto(1, mutableListOf(
            SimpleItemDto(1, "아이템1"),
            SimpleItemDto(2, "아이템2"),
            SimpleItemDto(3, "아이템3")
        )
        ),
        "섹션2",
        VerticalItemDto(3, mutableListOf(
            SimpleItemDto(5, "아이템1"),
            SimpleItemDto(6, "아이템2"),
            SimpleItemDto(7, "아이템3")
        )),
        "섹션3",
        ImageItemDto(9, R.drawable.photo01, "포토01"),
        ImageItemDto(10, R.drawable.photo02, "포토02"),
        ImageItemDto(11, R.drawable.photo03, "포토03"),
        "섹션4",
        HorizontalItemDto(13, listOf(
            R.drawable.photo01, R.drawable.photo02, R.drawable.photo03,
            R.drawable.photo04, R.drawable.photo05, R.drawable.photo06
        ))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            adapter = ItemAdapter().apply {
                submitList(dataList)
            }
        }//.also(ItemTouchHelper(ItemTouchCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT))::attachToRecyclerView)
    }

    inner class ItemTouchCallback(dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return viewHolder is VerticalItemAdapter.SimpleViewHolder
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewHolder.adapterPosition.also { position ->
                dataList.removeAt(position)
                recycler_view.adapter?.notifyItemRemoved(position)
            }
        }

        override fun onMoved(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, fromPos: Int, target: RecyclerView.ViewHolder, toPos: Int, x: Int, y: Int) {
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
            Collections.swap(dataList, fromPos, toPos)
            recyclerView.adapter?.notifyItemMoved(fromPos, toPos)
        }
    }
}