package com.example.chat_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chat_mobile.R.id.*
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        btnRegister = findViewById(btn_signUp_registration)
        btnLogin = findViewById(btn_signUp_login)
        email = findViewById(etEmail)
        password = findViewById(etPassword)


        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            checkValidData(email.text.toString(), password.text.toString())
        }

    }

    private fun checkValidData(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "email is required", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "password is required", Toast.LENGTH_SHORT).show()
            return
        }
        loginCheckUser(email, password)

    }
    private fun loginCheckUser(emailLog: String, passwordLog: String) {
        auth.signInWithEmailAndPassword(emailLog, passwordLog)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    email.setText("")
                    password.setText("")
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
    }
}