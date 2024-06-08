package com.examples.paycoin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App:Application() {
    companion object{
        lateinit var prefs: SharedPreferences

    }
    override fun onCreate() {
        super.onCreate()

        initPrefs()
    }
    private fun initPrefs(){
        prefs =  getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
}