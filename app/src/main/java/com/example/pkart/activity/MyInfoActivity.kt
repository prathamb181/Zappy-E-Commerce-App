package com.example.pkart.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pkart.R
import com.example.pkart.databinding.ActivityMyInfoBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyInfoBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = this.getSharedPreferences("users", MODE_PRIVATE)

        loadUserInfo()

        binding.btnUpdate.setOnClickListener {
            validateData(
                binding.userName.text.toString(),
                binding.userNumber.text.toString(),
                binding.userVillage.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString(),
                binding.userPincode.text.toString()
            )
        }

    }
    private fun validateData(
        name: String,
        number: String,
        village: String,
        city: String,
        state: String,
        pincode: String,
    ) {

        if (number.isEmpty() || state.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            storeData(pincode, city, state, village)
        }

    }

    private fun storeData(pincode: String, city: String, state: String, village: String) {
        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pincode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .update(map).addOnSuccessListener {

                val b = Bundle()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)


            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }


    private fun loadUserInfo() {

        Log.e("error",preferences.getString("number", "")!!)

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userVillage.setText(it.getString("village"))
                binding.userState.setText(it.getString("state"))
                binding.userCity.setText(it.getString("city"))
                binding.userPincode.setText(it.getString("pinCode"))
            }

            .addOnFailureListener {
                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()

            }
    }

}