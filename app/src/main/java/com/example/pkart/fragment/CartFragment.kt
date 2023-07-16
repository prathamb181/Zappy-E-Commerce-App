package com.example.pkart.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.pkart.R
import com.example.pkart.activity.AddressActivity
import com.example.pkart.adapter.CartAdapter
import com.example.pkart.databinding.FragmentCartBinding
import com.example.pkart.roomdb.AppDatabase
import com.example.pkart.roomdb.ProductModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var list: ArrayList<String>
    private lateinit var adapter : CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)

        val preferences =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()
        adapter = CartAdapter(requireContext(), emptyList())
        binding.cartRecycler.adapter = adapter
        dao.getAllProducts().observe(requireActivity()) {
            adapter = CartAdapter(requireContext(), it)
            binding.cartRecycler.adapter = adapter

            list.clear()
            for (data in it) {
                list.add(data.productId)
            }

            totalCost(it)
        }

        return binding.root

    }

    private fun totalCost(data: List<ProductModel>?) {
        var total = 0
        for (item in data!!) {
            total += item.productSP!!.toInt()
        }

        binding.textView12.text = "Total item in cart is ${data.size}"
        binding.textView13.text = "Total Cost : $total"


        binding.checkout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)
            val b = Bundle()
            b.putStringArrayList("productIds", list)
            b.putString("totalCost", total.toString())
            Log.v("cost",total.toString())
            intent.putExtras(b)
            startActivity(intent)

//            intent.putExtra("totalCost", total.toString())
//            intent.putStringArrayListExtra("productIds", list)


        }
    }
}