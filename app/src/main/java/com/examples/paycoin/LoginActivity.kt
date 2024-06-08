package com.examples.paycoin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LoginActivity:AppCompatActivity(R.layout.activity_login) {


    private lateinit var enterButton:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews(){
        enterButton = findViewById(R.id.enterButton)
    }

    private fun initListeners(){
        enterButton.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}