package com.hhp227.recyclerviewstudy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.recyclerviewstudy.R
import com.hhp227.recyclerviewstudy.adapter.ItemAdapter
import com.hhp227.recyclerviewstudy.model.ItemDto
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*

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
        }
    }

    inner class ItemTouchCallback(dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            TODO("Not yet implemented")
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            TODO("Not yet implemented")
        }
    }
}