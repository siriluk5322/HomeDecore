package com.example.projecthomedecor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_order_summary.*
import kotlinx.android.synthetic.main.fragment_search.*

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.hide()
        if (Global.loginStatus == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.cartframe,
               BasketEmptyFragment()
            ).commit()
        }else{
            supportFragmentManager.beginTransaction().add(
                R.id.cartframe,
                BasketFragment()
            ).commit()
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(Global.restartCart == "yes"){
            Toast.makeText(applicationContext,"ลบรายการสำเร็จ",Toast.LENGTH_LONG).show()
            Global.restartCart = "no"
            try {
                finish()
                startActivity(getIntent())
            }catch (a:Throwable){}


        }
    }
    override fun onResume() {
        super.onResume()


    }
    fun gohome(item: MenuItem) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    fun gocategory(item: MenuItem) {
        val intent = Intent(this, Category::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    fun gocart(item: MenuItem) {

    }
    fun goprofile(item: MenuItem) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    fun buyproduct(view: View) {
        if(Global.cart.size == 0){
            Toast.makeText(applicationContext,"คุณยังไม่เลือกสินค้าลงในรถเข็น",Toast.LENGTH_LONG).show()
        }else{
            supportFragmentManager.beginTransaction().add(
                R.id.cartframe,
                OrderSummaryFragment()
            ).addToBackStack(null).commit()
        }
    }

    fun acceptproduct(view: View) {
        var orderId = DatabaseApi.addOrder(sub_total.text.toString().toFloat())
        Toast.makeText(applicationContext, "ยืนยันคำสั่งซื้อสำเร็จ", Toast.LENGTH_LONG).show()
        DatabaseApi.addorderdetail(orderId)

        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

}