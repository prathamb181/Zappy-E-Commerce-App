package com.example.pkart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.databinding.AllOrdersItemLayoutBinding
import com.example.pkart.model.AllOrderModel

class AllOrderAdapter(val list : ArrayList<AllOrderModel>, val context: Context)
    : RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>(){

    inner class AllOrderViewHolder(val binding : AllOrdersItemLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrdersItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTitle.text = list[position].name
        holder.binding.productPrice.text = list[position].price
        when(list[position].status){
            "Ordered" -> {
                holder.binding.productStatus.text = "Ordered"
            }
            "Dispatched" -> {
                holder.binding.productStatus.text = "Dispatched"
            }
            "Delivered" -> {
                holder.binding.productStatus.text = "Delivered"
            }
            "Canceled" -> {
                holder.binding.productStatus.text = "Canceled"
            }
        }
    }

}