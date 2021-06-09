package com.hhp227.recyclerviewstudy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.recyclerviewstudy.R
import com.hhp227.recyclerviewstudy.adapter.ItemAdapter
import com.hhp227.recyclerviewstudy.model.ItemDto
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val dataList: ArrayList<*> = arrayListOf(
        "테스트",
        ItemDto(1, "아이템1"),
        ItemDto(2, "아이템2"),
        ItemDto(3, "아이템3")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            adapter = ItemAdapter().apply {
                submitList(dataList)
                setOnItemClickListener { _, i ->
                    if (dataList[i] is ItemDto) {
                        val item = dataList[i] as ItemDto
                        val intent = Intent(applicationContext, DetailActivity::class.java).putExtra("id", item.id)
                            .putExtra("pos", i)
                            .putExtra("name", item.name)

                        startActivity(intent)
                    }
                }
            }
        }.also(ItemTouchHelper(ItemTouchCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT))::attachToRecyclerView)
    }

    inner class ItemTouchCallback(dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return viewHolder !is ItemAdapter.HeaderViewHolder
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