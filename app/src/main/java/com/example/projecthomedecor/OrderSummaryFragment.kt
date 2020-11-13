package com.example.projecthomedecor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_order_summary.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderSummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderSummaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerOrder.layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerOrder.addItemDecoration(
            DividerItemDecoration(
                recyclerOrder.context,
                DividerItemDecoration.VERTICAL
            )
        )

    }
    override fun onResume() {
        super.onResume()
        loadCart()
        addr_cus.text = "ที่อยู่          :"+DatabaseApi.getUserAddress()
        updatePrice()
    }
    fun loadCart(){
        var dataList = arrayListOf<Cart>()
        dataList= Global.cart
        recyclerOrder.adapter = OrderSummaryAdapter(dataList, activity!!.applicationContext)

    }
    fun updatePrice(){
        if (Global.cart.size == 0){
            sub_total.text = "0"
        }else{
            var sum = 0F
            for (i in 0 until Global.cart.size) {
                val item = Global.cart[i]
                var productDetail = DatabaseApi.getProductWithProductId(item.product_id)
                sum += (productDetail!!.productPrice!!*item.amount)

            }
            sub_total.text = "${sum}"
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_summary, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderSummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderSummaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}