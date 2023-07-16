package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pkart.R
import com.example.pkart.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.ktx.Firebase

class OTPActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.button3.setOnClickListener{
            if(binding.userOTP.text!!.isEmpty())
                Toast.makeText(this, "Please provide OTP", Toast.LENGTH_SHORT).show()
            else{
                verifyUser(binding.userOTP.text.toString())
            }
        }
    }

    private fun verifyUser(otp: String) {

        val credential = PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!,otp)

        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("YES", "signInWithCredential:success")
                    val preferences = this.getSharedPreferences("users", MODE_PRIVATE)
                    val editor = preferences.edit()

                    editor.putString("number",intent.getStringExtra("number")!!)
                    editor.apply()

                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    Log.w("NO", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}