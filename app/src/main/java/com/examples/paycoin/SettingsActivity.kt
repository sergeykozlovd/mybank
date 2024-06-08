package com.examples.paycoin

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

const val KEY_LOGIN = "Login"
const val KEY_MERCHANT_ID = "MerchantId"
const val KEY_BTC = "BTC"
const val KEY_IMAGE_PATH = "ImagePath"
const val KEY_AMOUNT = "Amount"
const val KEY_PAY_TIMEOUT1 = "KEY_PAY_TIMEOUT1"
const val KEY_PAY_TIMEOUT2 = "KEY_PAY_TIMEOUT2"
const val KEY_PAY_TIMEOUT3 = "KEY_PAY_TIMEOUT3"
const val KEY_MESSAGE_SUCCESS_DIALOG = "KEY_MESSAGE_SUCCESS_DIALOG"
const val KEY_MESSAGE_FAILURE_DIALOG = "KEY_MESSAGE_FAILURE_DIALOG"
const val KEY_SWITCH_SUCCESS1 = "KEY_SWITCH_SUCCESS1"
const val KEY_SWITCH_SUCCESS2 = "KEY_SWITCH_SUCCESS2"
const val KEY_SWITCH_SUCCESS3 = "KEY_SWITCH_SUCCESS3"
const val KEY_MESSAGE_DIALOG = "KEY_MESSAGE_DIALOG"
const val KEY_PAY_TIMEOUT = "KEY_PAY_TIMEOUT"
const val KEY_IS_SUCCESS = "KEY_IS_SUCCESS"


class SettingsActivity : AppCompatActivity(R.layout.activity_settings) {

    private lateinit var viewModel: MainViewModel
    private lateinit var toolbar: Toolbar

    private lateinit var etLogin: TextInputEditText
    private lateinit var etMerchantId: TextInputEditText
    private lateinit var etBtc: TextInputEditText

    private lateinit var button: MaterialButton
    private lateinit var secretSettingsButton: TextView

    private lateinit var layoutMessageSuccessDialog: TextInputLayout
    private lateinit var layoutMessageFailureDialog: TextInputLayout
    private lateinit var etMessageSuccessDialog: TextInputEditText
    private lateinit var etMessageFailureDialog: TextInputEditText
    private lateinit var layoutPayTimeOut1: LinearLayout
    private lateinit var layoutPayTimeOut2: LinearLayout
    private lateinit var layoutPayTimeOut3: LinearLayout
    private lateinit var etPayTimeOut1: TextView
    private lateinit var etPayTimeOut2: TextView
    private lateinit var etPayTimeOut3: TextView
    private lateinit var switchSuccess1: SwitchCompat
    private lateinit var switchSuccess2: SwitchCompat
    private lateinit var switchSuccess3: SwitchCompat


//    private lateinit var hiddenView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initViews()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.payTimeOut1LiveData.observe(this) { etPayTimeOut1.text = it.toString() }
        viewModel.payTimeOut2LiveData.observe(this) { etPayTimeOut2.text = it.toString() }
        viewModel.payTimeOut3LiveData.observe(this) { etPayTimeOut3.text = it.toString() }

        viewModel.messageSuccessDialogLiveData.observe(this) { etMessageSuccessDialog.setText(it) }
        viewModel.messageFailureDialogLiveData.observe(this) { etMessageFailureDialog.setText(it) }

        viewModel.switchSuccess1LiveData.observe(this) { switchSuccess1.isChecked = it }
        viewModel.switchSuccess2LiveData.observe(this) { switchSuccess2.isChecked = it }
        viewModel.switchSuccess3LiveData.observe(this) { switchSuccess3.isChecked = it }

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
            viewModel.payTimeOut1LiveData.value = Integer.parseInt(etPayTimeOut1.text.toString())
            viewModel.payTimeOut2LiveData.value = Integer.parseInt(etPayTimeOut2.text.toString())
            viewModel.payTimeOut3LiveData.value = Integer.parseInt(etPayTimeOut3.text.toString())
            viewModel.messageSuccessDialogLiveData.value = etMessageSuccessDialog.text.toString()
            viewModel.messageFailureDialogLiveData.value = etMessageFailureDialog.text.toString()
            viewModel.switchSuccess1LiveData.value = switchSuccess1.isChecked
            viewModel.switchSuccess2LiveData.value = switchSuccess2.isChecked
            viewModel.switchSuccess3LiveData.value = switchSuccess3.isChecked

            App.prefs.edit().apply {
                putString(KEY_LOGIN, viewModel.loginLiveData.value ?: "")
                putString(KEY_MERCHANT_ID, viewModel.merchantIdLiveData.value ?: "")
                putString(KEY_BTC, viewModel.btcLiveData.value ?: "")
                putInt(KEY_PAY_TIMEOUT1, viewModel.payTimeOut1LiveData.value ?: 0)
                putInt(KEY_PAY_TIMEOUT2, viewModel.payTimeOut2LiveData.value ?: 0)
                putInt(KEY_PAY_TIMEOUT3, viewModel.payTimeOut3LiveData.value ?: 0)
                putString(
                    KEY_MESSAGE_SUCCESS_DIALOG,
                    viewModel.messageSuccessDialogLiveData.value ?: ""
                )
                putString(
                    KEY_MESSAGE_FAILURE_DIALOG,
                    viewModel.messageFailureDialogLiveData.value ?: ""
                )
                putBoolean(KEY_SWITCH_SUCCESS1, viewModel.switchSuccess1LiveData.value ?: true)
                putBoolean(KEY_SWITCH_SUCCESS2, viewModel.switchSuccess2LiveData.value ?: true)
                putBoolean(KEY_SWITCH_SUCCESS3, viewModel.switchSuccess3LiveData.value ?: true)
            }.apply()
            finish()
        }

        secretSettingsButton.setOnLongClickListener {
//            hiddenView.visibility = View.VISIBLE
            layoutMessageSuccessDialog.visibility = View.VISIBLE
            layoutMessageFailureDialog.visibility = View.VISIBLE
            layoutPayTimeOut1.visibility = View.VISIBLE
            layoutPayTimeOut2.visibility = View.VISIBLE
            layoutPayTimeOut3.visibility = View.VISIBLE
            etLogin.isEnabled = true
            etMerchantId.isEnabled = true
            false
        }
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        button = findViewById(R.id.button)
//        etPayTimeOut = findViewById(R.id.etPayTimeOut1)
        secretSettingsButton = findViewById(R.id.secretSettingsButton)
//        hiddenView = findViewById(R.id.hiddenView)
//        etMessageDialog = findViewById(R.id.etMessageSuccessDialog)
        etLogin = findViewById(R.id.etLogin)
        etMerchantId = findViewById(R.id.etMerchantId)
        etBtc = findViewById(R.id.etBtc)

//        layoutMessageDialog = findViewById(R.id.layoutMessageSuccessDialog)
//        layoutPayTimeOut = findViewById(R.id.layoutPayTimeOut1)

        layoutMessageSuccessDialog = findViewById(R.id.layoutMessageSuccessDialog)
        layoutMessageFailureDialog = findViewById(R.id.layoutMessageFailureDialog)
        etMessageSuccessDialog = findViewById(R.id.etMessageSuccessDialog)
        etMessageFailureDialog = findViewById(R.id.etMessageFailureDialog)
        layoutPayTimeOut1 = findViewById(R.id.layoutPayTimeOut1)
        layoutPayTimeOut2 = findViewById(R.id.layoutPayTimeOut2)
        layoutPayTimeOut3 = findViewById(R.id.layoutPayTimeOut3)
        etPayTimeOut1 = findViewById(R.id.etPayTimeOut1)
        etPayTimeOut2 = findViewById(R.id.etPayTimeOut2)
        etPayTimeOut3 = findViewById(R.id.etPayTimeOut3)
        switchSuccess1 = findViewById(R.id.switchSuccess1)
        switchSuccess2 = findViewById(R.id.switchSuccess2)
        switchSuccess3 = findViewById(R.id.switchSuccess3)


    }

    private fun initViewModel() {
        viewModel = MainActivity.viewModelProvider.get(MainViewModel::class.java)
    }

}