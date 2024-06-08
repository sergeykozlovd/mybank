package com.examples.paycoin

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

const val KEY_LOGIN = "Login"
const val KEY_MERCHANT_ID = "MerchantId"
const val KEY_BTC = "BTC"
const val KEY_IMAGE_PATH = "ImagePath"
const val KEY_AMOUNT = "Amount"
const val KEY_PAY_TIMEOUT = "PayTimeOut"
const val KEY_MESSAGE_DIALOG = "DialogMessage"

class SettingsActivity : AppCompatActivity(R.layout.activity_settings) {

    private lateinit var viewModel: MainViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var etPayTimeOut: TextView
    private lateinit var etLogin: TextInputEditText
    private lateinit var etMerchantId: TextInputEditText
    private lateinit var etBtc: TextInputEditText
    private lateinit var etMessageDialog: TextInputEditText
    private lateinit var button: MaterialButton
    private lateinit var secretSettingsButton: TextView

    private lateinit var layoutMessageDialog: TextInputLayout
    private lateinit var layoutPayTimeOut: TextInputLayout
//    private lateinit var hiddenView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initViews()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.payTimeOutLiveData.observe(this) {
            etPayTimeOut.text = it.toString()
        }

        viewModel.messageDialogLiveData.observe(this) {
            etMessageDialog.setText(it)
        }

        viewModel.loginLiveData.observe(this) {
            etLogin.setText(it)
        }

        viewModel.merchantIdLiveData.observe(this) {
            etMerchantId.setText(it)
        }

        viewModel.btcLiveData.observe(this) {
            etBtc.setText(it)
        }
    }

    private fun initListeners() {
        toolbar.setNavigationOnClickListener {
            finish()
        }

        button.setOnClickListener {

            viewModel.loginLiveData.value = etLogin.text.toString()
            viewModel.merchantIdLiveData.value = etMerchantId.text.toString()
            viewModel.btcLiveData.value = etBtc.text.toString()
            viewModel.payTimeOutLiveData.value = Integer.parseInt(etPayTimeOut.text.toString())
            viewModel.messageDialogLiveData.value = etMessageDialog.text.toString()


            App.prefs.edit().apply {
                putString(KEY_LOGIN, viewModel.loginLiveData.value ?: "")
                putString(KEY_MERCHANT_ID, viewModel.merchantIdLiveData.value ?: "")
                putString(KEY_BTC, viewModel.btcLiveData.value ?: "")
                putInt(KEY_PAY_TIMEOUT, viewModel.payTimeOutLiveData.value ?: 0)
                putString(KEY_MESSAGE_DIALOG, viewModel.messageDialogLiveData.value ?: "")
            }.apply()
            finish()
        }

        secretSettingsButton.setOnLongClickListener {
//            hiddenView.visibility = View.VISIBLE
            layoutMessageDialog.visibility  = View.VISIBLE
            layoutPayTimeOut.visibility  = View.VISIBLE
            etLogin.isEnabled = true
            etMerchantId.isEnabled = true
            false
        }
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        button = findViewById(R.id.button)
        etPayTimeOut = findViewById(R.id.etPayTimeOut)
        secretSettingsButton = findViewById(R.id.secretSettingsButton)
//        hiddenView = findViewById(R.id.hiddenView)
        etMessageDialog = findViewById(R.id.etMessageDialog)
        etLogin = findViewById(R.id.etLogin)
        etMerchantId = findViewById(R.id.etMerchantId)
        etBtc = findViewById(R.id.etBtc)

        layoutMessageDialog = findViewById(R.id.layoutMessageDialog)
        layoutPayTimeOut = findViewById(R.id.layoutPayTimeOut)
    }

    private fun initViewModel() {
        viewModel = MainActivity.viewModelProvider.get(MainViewModel::class.java)
    }


    /*
    Что должны видеть в шестеренке:
    1. Не меняемые пользователем слова
    2. Не меняемые пользователем слова
    3. Поле меняемые пользователем

    Скрытые настройки:
    1. Секунды
    2. Слова для шестеренки
    3. Слова для шестеренки
    4. Слова после совершения оплаты
     */


}