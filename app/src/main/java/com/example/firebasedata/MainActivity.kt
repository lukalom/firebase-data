package com.example.firebasedata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var inputEmail:EditText
    private lateinit var inputPassword:EditText
    private lateinit var loginButton:Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null){

            goToPerson()
        }

        setContentView(R.layout.activity_main)

//        mAuth = FirebaseAuth.getInstance()

        inputEmail =  findViewById(R.id.emailEditText)
        inputPassword = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val email  = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show()

            }else{
                //mauth.signInWithEmailAndPassword(email,password) unda mivmarot am metods rata
                // gadamowmos aris tuara bazashi es email da password
                //xolo addonccompletelistenershi chagvijdeba pasuxi
                    //taskshi jdeba pasxui
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
                    if (task.isSuccessful){
                        goToPerson()
                    }else{
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }

                }


            }
        }

    }
    private fun goToPerson(){
        startActivity(Intent(this,PersoActivity::class.java))
        finish()
    }
}