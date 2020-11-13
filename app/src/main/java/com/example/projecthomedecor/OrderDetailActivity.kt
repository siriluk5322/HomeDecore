package com.example.projecthomedecor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notification_shipping.*
import kotlinx.android.synthetic.main.activity_order_datail_user.*
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.reccom_recycler_view

class OrderDetailActivity : AppCompatActivity() {
    var orderID:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_order_detail)
//totalPrice
        orderID = intent.getStringExtra("orderID")
        var totalPrice = intent.getStringExtra("totalPrice")
        var status = intent.getStringExtra("status")
        var date = intent.getStringExtra("date")
        var  user = intent.getStringExtra("user")
        var trackingnumber = intent.getStringExtra("trackingnumber")
        if(trackingnumber == "null"){
            showtracking.setText(("ยังไม่จัดส่ง"))
        }else{
            showtracking.setText((trackingnumber))
        }
        orderDetailid.setText(orderID)
        orderDetailTotalPrice.setText(totalPrice)
        statusPayment.setText(status)
        showdate.setText(date)
        showuser.setText(user)

        var dataList = arrayListOf<ProductRecommend>()
        dataList= DatabaseApi.getOrderDetail(orderID!!)
        println(dataList.toString())
        recyclerviewOrderDeatil.adapter = OrderDetailAdminAdapter(dataList, applicationContext)
        recyclerviewOrderDeatil.layoutManager = LinearLayoutManager(applicationContext)
        recyclerviewOrderDeatil.itemAnimator = DefaultItemAnimator()
    }

    fun SentTracking(view: View) {
        val intent = Intent(this,NotificationShippingActivity::class.java)
        intent.putExtra("orderID",orderID)
        startActivity(intent)}
}