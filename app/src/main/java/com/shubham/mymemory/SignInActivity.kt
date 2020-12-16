package com.shubham.mymemory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {


    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mLoginBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.LoginBtn);

        mLoginBtn.setOnClickListener {
            var email=mEmail.text.toString().trim()
            var password=mPassword.text.toString().trim()
            if(TextUtils.isEmpty(email)){

                mEmail.setError("Email is required")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is required")
                return@setOnClickListener
            }
            progressBar.visibility= View.VISIBLE
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java));
                }else {
                    Toast.makeText(this, "Error ! " + it.exception!!.message, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        }
    }
}