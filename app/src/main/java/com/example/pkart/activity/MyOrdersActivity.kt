package com.example.pkart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.pkart.R
import com.example.pkart.adapter.AllOrderAdapter
import com.example.pkart.databinding.ActivityMyOrdersBinding
import com.example.pkart.databinding.FragmentMoreBinding
import com.example.pkart.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyOrdersActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyOrdersBinding
    private lateinit var list : ArrayList<AllOrderModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()

        val preferences = this.getSharedPreferences("users", AppCompatActivity.MODE_PRIVATE)


        Firebase.firestore.collection("allOrders").whereEqualTo("userId",preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it){
                    val data = doc.toObject(AllOrderModel::class.java)
                    list.add(data)
                }
                binding.recyclerView.adapter = AllOrderAdapter(list,this)
            }
    }
}