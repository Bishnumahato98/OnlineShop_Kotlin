package com.example.onlineshop.ui.activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineshop.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val username = findViewById<EditText>(R.id.etUsername)
        val email = findViewById<EditText>(R.id.etEmail)
        val contact = findViewById<EditText>(R.id.etContact)
        val password = findViewById<EditText>(R.id.etPassword)
        val confirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnSignup.setOnClickListener {
            val usernameText = username.text.toString().trim()
            val emailText = email.text.toString().trim()
            val contactText = contact.text.toString().trim()
            val passwordText = password.text.toString().trim()
            val confirmPasswordText = confirmPassword.text.toString().trim()

            if (usernameText.isEmpty() || emailText.isEmpty() || contactText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (passwordText != confirmPasswordText) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(usernameText, emailText, contactText, passwordText)
            }
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(username: String, email: String, contact: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val user = User(username, email, contact)

                    if (userId != null) {
                        database.getReference("Users").child(userId).setValue(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Database Error: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Toast.makeText(this, "Signup Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    data class User(val username: String, val email: String, val contact: String)
}

