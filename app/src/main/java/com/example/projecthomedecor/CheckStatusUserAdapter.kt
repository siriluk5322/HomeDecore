package com.example.projecthomedecor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_order_datail_user.view.*
import kotlinx.android.synthetic.main.check_status_user_layout.view.*


class CheckStatusUserAdapter(val items: ArrayList<Orders>, val context: Context) : RecyclerView.Adapter<CheckStatusUserAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var OrderId = view.status_id
            var Totalprice = view.status_price
            var trackingnumber = view.trackingnumber
            var paymentUser = view.paymentUser
            var status = view.statuspay
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view_ckeckOreder = LayoutInflater.from(parent.context)
            .inflate(R.layout.check_status_user_layout, parent, false)
        val myHolder = CheckStatusUserAdapter.ViewHolder(view_ckeckOreder)
        view_ckeckOreder.setOnClickListener() {
            val pos = myHolder.adapterPosition
            val context: Context = parent.context
            val orderCheck = items[pos]
           val intent = Intent(context, OrderDatailUserActivity::class.java)
           intent.putExtra("orderID",orderCheck.Order_id)
           intent.putExtra("totalPrice",orderCheck.Orders_totalprice)
            intent.putExtra("status",orderCheck.Orders_status)
            intent.putExtra("trackingnumber",orderCheck.trackingnumber)
            intent.putExtra("date",orderCheck.Orders_date)
           context.startActivity(intent)
        }
        return myHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.OrderId.text = items[position].Order_id
        holder.Totalprice.text = items[position].Orders_totalprice
        if(items[position].trackingnumber == "null"){
            holder.trackingnumber.text = "ยังไม่จัดส่ง"
        }else{
            holder.trackingnumber.text = items[position].trackingnumber
        }
        holder.status.text = items[position].Orders_status
        if(items[position].Orders_status == "paid"){
            holder.paymentUser.visibility = View.INVISIBLE
        }else{
            holder.paymentUser.visibility = View.VISIBLE
        }
        holder.paymentUser.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(DatabaseApi.genPayment(
                items[position].Orders_totalprice.toString().toFloat(),
                items[position].Order_id.toString().toInt()
            )))
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(browserIntent)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}