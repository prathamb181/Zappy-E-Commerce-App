package com.example.pkart.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.R
import com.example.pkart.activity.AddressActivity
import com.example.pkart.activity.LoginActivity
import com.example.pkart.activity.MyInfoActivity
import com.example.pkart.activity.MyOrdersActivity
import com.example.pkart.adapter.AllOrderAdapter
import com.example.pkart.databinding.FragmentCartBinding
import com.example.pkart.databinding.FragmentMoreBinding
import com.example.pkart.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MoreFragment : Fragment() {

    private lateinit var binding : FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)
        binding.btnMyOrders.setOnClickListener {
            val intent = Intent(context, MyOrdersActivity::class.java)
            startActivity(intent)
        }

        binding.btnMyInfo.setOnClickListener {
            val intent = Intent(context, MyInfoActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}