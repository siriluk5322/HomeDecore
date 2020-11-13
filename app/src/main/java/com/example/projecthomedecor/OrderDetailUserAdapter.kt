package com.example.projecthomedecor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_order_datail_user.view.*
import kotlinx.android.synthetic.main.order_detail_admin_layout.view.*

class OrderDetailUserAdapter  (val items : List<ProductRecommend>, val context: Context): RecyclerView.Adapter<OrderDetailUserAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var productName = view.orderdetail_name
        var price = view.orderdetail_price
        var amount = view.orderdetail_amount
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailUserAdapter.ViewHolder {
        val view_orderdatailUser = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_detail_admin_layout, parent, false)
        val myHolder = OrderDetailUserAdapter.ViewHolder(view_orderdatailUser)
        return myHolder
    }

    override fun onBindViewHolder(holder: OrderDetailUserAdapter.ViewHolder, position: Int) {
        holder.productName.text = "ชื่อสินค้า : " +items[position].productName
        holder.price.text = "ราคาสินค้า : " +items[position].productPrice.toString()
        holder.amount.text = "จำนวนสินค้า : "+items[position].balancestock.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}