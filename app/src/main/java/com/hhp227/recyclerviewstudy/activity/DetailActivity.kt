package com.hhp227.recyclerviewstudy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hhp227.recyclerviewstudy.R
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
    }
}