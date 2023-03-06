package com.example.chat_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.chat_mobile.R.layout.activity_sign_up
import com.example.chat_mobile.R.id.*




class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var btnRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_sign_up)

        auth = FirebaseAuth.getInstance()

        // Initialize the views here
        userName = findViewById(etName)
        email = findViewById(etEmail)
        password = findViewById(etPassword)
        confirmPassword = findViewById(etConfirmPassword)
        btnRegister = findViewById(btn_signUp_registration)



        btnRegister.setOnClickListener {
            val userName = userName.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            val confirmPassword = confirmPassword.text.toString()

            checkValidData(userName, email, password, confirmPassword)
        }
    }

    private fun checkValidData(userName: String, email: String, password: String, confirmPassword: String) {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(applicationContext, "username is required", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "email is required", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "password is required", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(applicationContext, "confirm password is required", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length < 10) {
            Toast.makeText(applicationContext, "password < 10", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmPassword) {
            Toast.makeText(applicationContext, "password not match", Toast.LENGTH_SHORT).show()
            return
        }
        registerUser(userName, email, password)
    }

    private fun registerUser(userName: String, email : String, password : String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val HashMapUser:HashMap<String,String> = HashMap()
                    HashMapUser["userId"] = userId
                    HashMapUser["UserName"] = userName
                    HashMapUser["profileImage"] = ""

                    databaseReference.setValue(HashMapUser).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            // open home activity
                            val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
    }


}