package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var signupBtn: Button
    private lateinit var signinBtn: Button
    private lateinit var welcomeText: TextView
    private lateinit var logoutBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

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
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressbar)

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
            
            // Show RecyclerView and ProgressBar when logged in
            recyclerView.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE

            // 3. Load Products
            val url = "https://rolexbett.alwaysdata.net/api/products"
            val helper = ApiHelper(this)
            helper.loadProducts(url, recyclerView, progressBar)

        } else {
            // User is NOT logged in: Show signin/signup, hide welcome/logout/products
            welcomeText.visibility = View.GONE
            logoutBtn.visibility = View.GONE
            signupBtn.visibility = View.VISIBLE
            signinBtn.visibility = View.VISIBLE
            
            recyclerView.visibility = View.GONE
            progressBar.visibility = View.GONE
        }

        // 4. Set up Click Listeners
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
        // find the recyclerView and the progress bar by use of their IDs
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val progressbar = findViewById<ProgressBar>(R.id.progressbar)

//specify the API URL endpoint for fetching the products (alwaysData)
        val url = "https://kbenkamotho.alwaysdata.net/api/get_products"

// import the helper class
        val helper = ApiHelper(applicationContext)

// inside of the helper class, access the function loadproducts
        helper.loadProducts(url, recyclerView, progressbar)

// find the About button by use of its ID and have the intent

        val aboutButton = findViewById<Button>(R.id.aboutBtn)



//below is the intent to the About activity

        aboutButton.setOnClickListener {

            val intent = Intent(applicationContext, About::class.java)

            startActivity(intent)



        }    }
}