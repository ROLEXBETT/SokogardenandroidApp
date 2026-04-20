package com.example.sokogarden

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // find all views by their IDs
        val txtname = findViewById<TextView>(R.id.txtProductName)
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val txtCost = findViewById<TextView>(R.id.txtProductCost)
        val phoneEditText = findViewById<EditText>(R.id.phone)
        val payButton = findViewById<Button>(R.id.pay)

        // retrieve data from intent (matching keys used in ProductAdapter)
        val productName = intent.getStringExtra("product_name")
        val productPhoto = intent.getStringExtra("product_photo")
        val productCost = intent.getIntExtra("product_cost", 0)

        // update the views with the data
        txtname.text = productName
        txtCost.text = "Kes $productCost"

        // specify the image url
        val imageUrl = "https://kbenkamotho.alwaysdata.net/static/images/$productPhoto"

        // load the image using Glide
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imgProduct)

        // set up a click listener for the pay button
        payButton.setOnClickListener {
            val phoneNumber = phoneEditText.text.toString().trim()
            
            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // specify the API endpoint
            val api = "https://kbenkamotho.alwaysdata.net/api/payment"

            // create a RequestParams object
            val params = RequestParams()

            // insert the data into the params object
            params.put("amount", productCost)
            params.put("phone", phoneNumber)

            // Use the API helper to post data
            val helper = ApiHelper(this)
            helper.post(api, params)
            
            // clear the phone number field
            phoneEditText.text.clear()
        }
    }
}