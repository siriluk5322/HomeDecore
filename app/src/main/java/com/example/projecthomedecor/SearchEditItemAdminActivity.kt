package com.example.projecthomedecor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_edit_item_admin.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.edt_search
import kotlinx.android.synthetic.main.fragment_search.reccom_recycler_view

class SearchEditItemAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_edit_item_admin)
        supportActionBar?.hide()

        val productSearch = intent.getStringExtra("searchText")
        edt_search.setText(productSearch)

        var dataList = arrayListOf<ProductRecommend>()
        //Toast.makeText(applicationContext, if(productSearch!=null)productSearch else "no", Toast.LENGTH_LONG).show()
        dataList= DatabaseApi.getAllProduct(if(productSearch!=null)productSearch else "")
        recyclerSearchEditeAdmin2.adapter = ShowSearchItemsEditeDeleteAdapter(dataList, applicationContext)
        recyclerSearchEditeAdmin2.layoutManager = LinearLayoutManager(applicationContext)
        recyclerSearchEditeAdmin2.itemAnimator = DefaultItemAnimator()
    }

    fun clickSearchEdit(view: View) {
        val intent = Intent(this, SearchEditItemAdminActivity::class.java)
        intent.putExtra("searchText",edt_search.text.toString())
        finish()
        startActivity(intent)
    }
}