package com.examples.paycoin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity:AppCompatActivity(R.layout.activity_login) {


    private lateinit var enterButton:MaterialButton
    private lateinit var etLogin: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews(){
        enterButton = findViewById(R.id.enterButton)
        etLogin = findViewById(R.id.etLogin)

        etLogin.setText( App.prefs.getString(KEY_LOGIN,""))
    }

    private fun initListeners(){
        enterButton.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}