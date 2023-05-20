package com.anish.quickchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        mAuth = Firebase.auth

        edtName=findViewById<EditText>(R.id.edt_username)
        edtEmail=findViewById<EditText>(R.id.edt_email)
        edtPassword=findViewById<EditText>(R.id.edt_password)
        btnSignUp=findViewById<Button>(R.id.btn_signup)

        btnSignUp.setOnClickListener {
            val username=edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            if(email.isNotBlank() && password.isNotBlank() && username.isNotBlank()){
                signup(username,email,password)
            }else{
                Toast.makeText(this,"Please enter all the fields detail", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun signup(username:String,email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserTodb(username,email, mAuth.currentUser?.uid)
                    val intent = Intent(this@SignUp,Login::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Please Try Again!",Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserTodb(username: String, email: String, uid: String?) {
        mDbref = FirebaseDatabase.getInstance().reference
        if (uid != null) {
            mDbref.child("user").child(uid).setValue(User(username,email,uid))
        }
    }

}