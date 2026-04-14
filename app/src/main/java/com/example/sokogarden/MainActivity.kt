package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var signupBtn: Button
    private lateinit var signinBtn: Button
    private lateinit var welcomeText: TextView
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Initialize all Views
        signupBtn = findViewById(R.id.signupBtn)
        signinBtn = findViewById(R.id.signinBtn)
        welcomeText = findViewById(R.id.welcomeText)
        logoutBtn = findViewById(R.id.logoutBtn)

        // 2. Check login session
        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        val username = prefs.getString("username", null)

        if (username != null) {
            // User is logged in: Show welcome and logout, hide signin/signup
            welcomeText.text = "Welcome $username"
            welcomeText.visibility = View.VISIBLE
            logoutBtn.visibility = View.VISIBLE
            signupBtn.visibility = View.GONE
            signinBtn.visibility = View.GONE
        } else {
            // User is NOT logged in: Show signin/signup, hide welcome/logout
            welcomeText.visibility = View.GONE
            logoutBtn.visibility = View.GONE
            signupBtn.visibility = View.VISIBLE
            signinBtn.visibility = View.VISIBLE
        }

        // 3. Set up Click Listeners
        signupBtn.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }

        signinBtn.setOnClickListener {
            startActivity(Intent(this, Signin::class.java))
        }

        logoutBtn.setOnClickListener {
            prefs.edit {
                clear()
            }
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            
            // Refresh MainActivity to update the UI
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}