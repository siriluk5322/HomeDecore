package com.example.projecthomedecor

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.activity_product.*
import java.net.URL


class ProductActivity : AppCompatActivity() {
    var productId : String? = null
    var productPrice : String? = null
    var balancestock : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_product)


        val productName = intent.getStringExtra("productName")
        productPrice  = intent.getStringExtra("productPrice")
        val productImg = intent.getStringExtra("productImg")
         productId= intent.getStringExtra("productId")
//        val productType = intent.getStringExtra("productType_name")
         balancestock = intent.getStringExtra("balancestock")
//Toast.makeText(applicationContext,"http://pc.mosmai.me:10080/product/${productId}/1.jpg", Toast.LENGTH_LONG).show()
        Glide.with(baseContext).load("http://pc.mosmai.me:10080/product/${productId}/1.jpg").signature(ObjectKey(System.currentTimeMillis().toString())).skipMemoryCache(true).into(imageView)
        product_detail_name.setText(productName)
        product_detail_price.setText(productPrice)
        product_detail_balancestock.setText(balancestock)
        product_detail_type.setText(DatabaseApi.getProductType(productId as String))

    }

    fun addbasket(view: View) {
        if (Global.loginStatus == null){
            Toast.makeText(applicationContext,"คุณต้องเข้าสู่ระบบก่อน",Toast.LENGTH_LONG).show()
        } else{
            if(balancestock!!.toInt() <= 0) {
                Toast.makeText(applicationContext,"สินค้าในคลังหมด",Toast.LENGTH_LONG).show()
            }else {
                CartFuntion.AddToCart(productId.toString().toInt(), 1)
                Toast.makeText(applicationContext, "เพิ่มสินค้าลงในรถเข็นเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show()
            }
        }
    }
}