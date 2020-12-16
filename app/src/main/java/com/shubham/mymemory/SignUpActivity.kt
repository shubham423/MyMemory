package com.shubham.mymemory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    private lateinit var mFullName:EditText
    private lateinit var mEmail:EditText
    private lateinit var mPassword:EditText
    private lateinit var mRegisterBtn:Button
    private lateinit var mLoginBtn:TextView
    private lateinit var progressBar:ProgressBar
    private lateinit var fAuth:FirebaseAuth
    private lateinit var fStore:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mRegisterBtn= findViewById(R.id.registerBtn);
        mLoginBtn   = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
        }

        mRegisterBtn.setOnClickListener {


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
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this, "user registered Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                }
                else{
                    Toast.makeText(this, "Error ! " + it.exception!!.message, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }

        mLoginBtn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
    }
}