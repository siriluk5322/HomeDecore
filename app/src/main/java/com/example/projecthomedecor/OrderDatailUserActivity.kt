package com.example.projecthomedecor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_order_datail_user.*
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.activity_order_detail.recyclerviewOrderDeatil

class OrderDatailUserActivity : AppCompatActivity() {
    var orderID:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_order_datail_user)

        orderID = intent.getStringExtra("orderID")
        var totalPrice = intent.getStringExtra("totalPrice")
        var status = intent.getStringExtra("status")
        var trackingnumber = intent.getStringExtra("trackingnumber")
        if(trackingnumber == "null"){
            shippingUser.setText(("ยังไม่จัดส่ง"))
        }else{
            shippingUser.setText((trackingnumber))
        }
        var date = intent.getStringExtra("date")
        orderDetailUserid.setText(orderID)
        orderDetailUserTotalPrice.setText(totalPrice)
        statusUserPayment.setText(status)

        dateUser.setText(date)

        var dataList = arrayListOf<ProductRecommend>()
        dataList= DatabaseApi.getOrderDetail(orderID!!)
        println(dataList.toString())
        recyclerviewOrderDeatilUser.adapter = OrderDetailUserAdapter(dataList, applicationContext)
        recyclerviewOrderDeatilUser.layoutManager = LinearLayoutManager(applicationContext)
        recyclerviewOrderDeatilUser.itemAnimator = DefaultItemAnimator()
    }
}