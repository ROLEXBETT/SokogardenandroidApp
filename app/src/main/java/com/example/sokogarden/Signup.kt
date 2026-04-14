package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        find all views by their IDs
        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val phone = findViewById<EditText>(R.id.Phone)
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val signingTextView = findViewById<TextView>(R.id.signintxt)

       //below when a person clicks on the TextView, he/she is navigated to the signin page
    signingTextView.setOnClickListener {
        val intent = Intent(this, Signin::class.java)
        startActivity(intent)
    }

//   on click of the signup button, we want to send the data to the API
        signupBtn.setOnClickListener {
            //specify the API endpoint
            val api = "https://rolexbett.alwaysdata.net/api/signup"
            
            //create a RequestParams object to hold the data to be sent to the API
            val params = RequestParams()
            
//            add/append the data to the params object
            params.put("username", username.text.toString().trim())
            params.put("email", email.text.toString().trim())
            params.put("password", password.text.toString().trim())
            params.put("phone", phone.text.toString().trim())

            // Initialize the API helper
            val helper = ApiHelper(this)
            
            // Use the post function from ApiHelper to send data
            helper.post(api, params)

            email.text.clear()
            password.text.clear()
            username.text.clear()
            phone.text.clear()

            //intent to the mainActivity page
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}