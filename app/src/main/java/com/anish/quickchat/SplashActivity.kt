package com.anish.quickchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mAuth = FirebaseAuth.getInstance()

        var user:FirebaseUser? = mAuth.currentUser

        if (user != null) {
            // User is signed in
            // Start home activity
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent);
        } else {
            // No user is signed in
            // start login activity
            val intent=Intent(this@SplashActivity,Login::class.java)
            startActivity(intent);
        }

        // close splash activity
        finish();
    }

    }
