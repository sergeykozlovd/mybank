package com.examples.paycoin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var viewModel: MainViewModel

    private lateinit var toolbar: Toolbar
    private lateinit var editText: EditText
    private lateinit var payButton: MaterialButton
    private lateinit var hiddenButton: View

    companion object {
        lateinit var viewModelProvider: ViewModelProvider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initViews()
        initListeners()
        getSettings()
        initObservers()
    }


    private fun initObservers() {
        viewModel.imagePathLiveData.observe(this) {
            it?.let { imagePath ->
                App.prefs.edit().apply {
                    putString(KEY_IMAGE_PATH, imagePath)
                }.apply()
            }
        }
    }

    private fun initViewModel() {
        viewModelProvider = ViewModelProvider(this)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun getSettings() {
        viewModel.loginLiveData.value = App.prefs.getString(KEY_LOGIN, "Login")
        viewModel.merchantIdLiveData.value = App.prefs.getString(KEY_MERCHANT_ID, "Merchant ID")
        viewModel.btcLiveData.value = App.prefs.getString(KEY_BTC, "BTC")
        viewModel.payTimeOut1LiveData.value = App.prefs.getInt(KEY_PAY_TIMEOUT1, 0)
        viewModel.payTimeOut2LiveData.value = App.prefs.getInt(KEY_PAY_TIMEOUT2, 0)
        viewModel.payTimeOut3LiveData.value = App.prefs.getInt(KEY_PAY_TIMEOUT3, 0)
        viewModel.switchSuccess1LiveData.value = App.prefs.getBoolean(KEY_SWITCH_SUCCESS1,true)
        viewModel.switchSuccess2LiveData.value = App.prefs.getBoolean(KEY_SWITCH_SUCCESS2,true)
        viewModel.switchSuccess3LiveData.value = App.prefs.getBoolean(KEY_SWITCH_SUCCESS3,true)
        viewModel.messageSuccessDialogLiveData.value =
            App.prefs.getString(KEY_MESSAGE_SUCCESS_DIALOG, "Payment Success")
        viewModel.messageFailureDialogLiveData.value =
            App.prefs.getString(KEY_MESSAGE_FAILURE_DIALOG, "Payment Failure")
        viewModel.imagePathLiveData.value = App.prefs.getString(KEY_IMAGE_PATH, null)
    }

    private fun initListeners() {
        toolbar.setNavigationOnClickListener {

        }

        hiddenButton.setOnLongClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            false
        }

        editText.addTextChangedListener {
            if (editText.text.length != 0) {
                if (Integer.parseInt(editText.text.toString()) > 0) {
                    payButton.isEnabled = true
                    return@addTextChangedListener
                }
            }
            payButton.isEnabled = false
        }

        payButton.setOnClickListener {
            if (editText.text.isNotEmpty() && !editText.text.equals("0")) {
                payButton.isEnabled = false
                when(viewModel.runCount){
                    1 -> {
                        val isSuccess = viewModel.switchSuccess1LiveData.value == true
                        val messageText = if (isSuccess) viewModel.messageSuccessDialogLiveData.value else viewModel.messageFailureDialogLiveData.value
                        startActivity(Intent(this, PaymentActivity::class.java).apply {
                            putExtra(KEY_AMOUNT, editText.text.toString().toInt())
                            putExtra(KEY_PAY_TIMEOUT, viewModel.payTimeOut1LiveData.value)
                            putExtra(KEY_MESSAGE_DIALOG, messageText)
                            putExtra(KEY_IS_SUCCESS, isSuccess)
                        })
                    }

                    2 -> {
                        val isSuccess = viewModel.switchSuccess2LiveData.value == true
                        val messageText = if (isSuccess) viewModel.messageSuccessDialogLiveData.value else viewModel.messageFailureDialogLiveData.value
                        startActivity(Intent(this, PaymentActivity::class.java).apply {
                            putExtra(KEY_AMOUNT, editText.text.toString().toInt())
                            putExtra(KEY_PAY_TIMEOUT, viewModel.payTimeOut1LiveData.value)
                            putExtra(KEY_MESSAGE_DIALOG, messageText)
                            putExtra(KEY_IS_SUCCESS, isSuccess)
                        })
                    }

                    3 -> {
                        val isSuccess = viewModel.switchSuccess3LiveData.value == true
                        val messageText = if (isSuccess) viewModel.messageSuccessDialogLiveData.value else viewModel.messageFailureDialogLiveData.value
                        startActivity(Intent(this, PaymentActivity::class.java).apply {
                            putExtra(KEY_AMOUNT, editText.text.toString().toInt())
                            putExtra(KEY_PAY_TIMEOUT, viewModel.payTimeOut1LiveData.value)
                            putExtra(KEY_MESSAGE_DIALOG, messageText)
                            putExtra(KEY_IS_SUCCESS, isSuccess)
                        })
                    }
                }

                editText.text.clear()
                if (viewModel.runCount > 2 ){
                    viewModel.runCount = 1
                } else {
                    viewModel.runCount = viewModel.runCount + 1
                }

            }
        }
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        editText = findViewById(R.id.editText)
        payButton = findViewById(R.id.payButton)
        hiddenButton = findViewById(R.id.hiddenButton)
        payButton.isEnabled = false
    }

}