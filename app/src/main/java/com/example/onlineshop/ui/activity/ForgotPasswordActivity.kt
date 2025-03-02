package com.example.onlineshop.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineshop.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var tvStatus: TextView // Declare tvStatus as a TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        val etForgotPasswordEmail: TextInputEditText = findViewById(R.id.etForgotPasswordEmail)
        val btnResetPassword: View = findViewById(R.id.btnResetPassword)
        val progressBar: View = findViewById(R.id.progressBar)
        tvStatus = findViewById(R.id.tvStatus) // Initialize tvStatus

        // Reset Password Button Click Listener
        btnResetPassword.setOnClickListener {
            val email = etForgotPasswordEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "bishnukumarmahato262@gmail.com", Toast.LENGTH_SHORT).show()
            } else {
                resetPassword(email)
            }
        }
    }

    private fun resetPassword(email: String) {
        // Show progress bar
        findViewById<View>(R.id.progressBar).visibility = View.VISIBLE
        tvStatus.visibility = View.GONE

        // Send password reset email
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                findViewById<View>(R.id.progressBar).visibility = View.GONE

                if (task.isSuccessful) {
                    // Password reset email sent successfully
                    tvStatus.text = "Password reset email sent. Check your inbox."
                    tvStatus.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                    tvStatus.visibility = View.VISIBLE
                } else {
                    // Failed to send password reset email
                    tvStatus.text = "Failed to send reset email. Please check the email address."
                    tvStatus.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                    tvStatus.visibility = View.VISIBLE
                }
            }
    }
}