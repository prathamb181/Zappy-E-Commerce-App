package com.example.pkart.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pkart.R
import com.example.pkart.adapter.CategoryAdapter
import com.example.pkart.adapter.ProductAdapter
import com.example.pkart.databinding.FragmentHomeBinding
import com.example.pkart.model.AddProductModel
import com.example.pkart.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter : ProductAdapter
    private lateinit var adapter2 :CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        val preferences =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)

        if (preferences.getBoolean("isCart", false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

        getCategories()

        getProducts()

        //getSliderImage()

        return binding.root
    }


    private fun getSliderImage() {
        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
            }
    }

    private fun getProducts() {
        val list = ArrayList<AddProductModel>()
        adapter = ProductAdapter(requireContext(), list) // Create the adapter instance
        binding.productRecyclerview.adapter = adapter
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                binding.productRecyclerview.adapter = ProductAdapter(requireContext(), list)
            }
    }

    private fun getCategories() {
        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecyclerview.adapter = CategoryAdapter(requireContext(),list)
            }
    }
}