package com.example.projecthomedecor

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_basket.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BasketFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasketFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerBasket.layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerBasket.addItemDecoration(
            DividerItemDecoration(
                recyclerBasket.context,
                DividerItemDecoration.VERTICAL
            )
        )

    }


    override fun onResume() {
        super.onResume()
        loadCart()
        order_address.text = "ที่อยู่ :"+DatabaseApi.getUserAddress()
        val mainHandler = Handler(Looper.getMainLooper())
        updatePrice()
        mainHandler.post(object : Runnable {
            override fun run() {
                try {
                    if(Global.updatePrice){
                        updatePrice()
                        Global.updatePrice = false
                    }

                }catch (t:Throwable){

                }

                mainHandler.postDelayed(this, 1000)
            }
        })

    }

    fun updatePrice(){
        if (Global.cart.size == 0){
            totalprice_order.text = "ราคารวมทั้งหมด 0 บาท"
        }else{
            var sum = 0F
            for (i in 0 until Global.cart.size) {
                val item = Global.cart[i]
                var productDetail = DatabaseApi.getProductWithProductId(item.product_id)
                sum += (productDetail!!.productPrice!!*item.amount)

            }
            totalprice_order.text = "ราคารวมทั้งหมด ${sum} บาท"
        }
    }
    fun loadCart(){
        var dataList = arrayListOf<Cart>()
        dataList= Global.cart
        recyclerBasket.adapter = CartAdapter(dataList, activity!!.applicationContext)
    }

    companion object {


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BasketFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BasketFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}