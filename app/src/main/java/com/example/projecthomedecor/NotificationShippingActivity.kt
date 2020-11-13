package com.example.projecthomedecor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_notification_shipping.*
import kotlinx.android.synthetic.main.fragment_search.*

class NotificationShippingActivity : AppCompatActivity() {
    var orderID:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_shipping)
        supportActionBar?.hide()

        orderID = intent.getStringExtra("orderID")
        orderid.setText(orderID)
    }

    fun SearchTracking(view: View) {
        DatabaseApi.updateTracking(orderID!!, tracking_number.text.toString())
        Toast.makeText(applicationContext,"แจ้งสถานะการจัดส่งสำเร็จ",Toast.LENGTH_LONG).show()
    }
}