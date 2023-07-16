package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pkart.databinding.ActivityProductDetailsBinding
import com.example.pkart.fragment.CartFragment
import com.example.pkart.roomdb.AppDatabase
import com.example.pkart.roomdb.ProductDao
import com.example.pkart.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)

        getProductDetails(intent.getStringExtra("id"))

        setContentView(binding.root)
    }

    private fun getProductDetails(productID: String?) {

        Firebase.firestore.collection("products")
            .document(productID!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                val name = it.getString("productName")
                val productSP = it.getString("productSP")
                val productDesc = it.getString("productDescription")

                binding.textView7.text = name
                binding.textView8.text = productSP
                binding.textView9.text = productDesc

                val slideList = ArrayList<SlideModel>()
                for(data in list){
                    slideList.add(SlideModel(data,ScaleTypes.CENTER_CROP))
                }

                cartAction(productID,name,productSP,it.getString("productCoverImg"))

                binding.imageSlider.setImageList(slideList)
            }.addOnFailureListener{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun cartAction(productID: String, name: String?, productSP: String?, coverImg: String?) {

        val productDao = AppDatabase.getInstance(this).productDao()

        if(productDao.exists(productID) != null){
            binding.textView10.text = "Go to Cart"
        }else{
            binding.textView10.text = "Add to Cart"
        }

        binding.textView10.setOnClickListener{
            if(productDao.exists(productID) != null){
                openCart()
            }else{
                addToCart(productDao,productID,name,productSP,coverImg)
            }
        }

    }

    private fun addToCart(productDao: ProductDao, productID: String, name: String?, productSP: String?, coverImg: String?) {

        val data = ProductModel(productID,name,coverImg,productSP)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.textView10.text = "Go to Cart"
        }

    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart",true)
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}