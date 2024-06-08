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

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var viewModel: MainViewModel

    private lateinit var toolbar: Toolbar
    private lateinit var editText: EditText
    private lateinit var payButton: TextView
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

//    private fun initDate() {
//        if (Date().after(Calendar.getInstance().apply { set(2022, 1, 30) }.time))
//            finish()
//    }

    private fun initViewModel() {
        viewModelProvider = ViewModelProvider(this)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun getSettings() {
        viewModel.loginLiveData.value = App.prefs.getString(KEY_LOGIN, "Login")
        viewModel.merchantIdLiveData.value = App.prefs.getString(KEY_MERCHANT_ID, "Merchant ID")
        viewModel.btcLiveData.value = App.prefs.getString(KEY_BTC, "BTC")
        viewModel.payTimeOutLiveData.value = App.prefs.getInt(KEY_PAY_TIMEOUT, 0)
        viewModel.messageDialogLiveData.value =
            App.prefs.getString(KEY_MESSAGE_DIALOG, "Payment Done")
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
                    payButton.setBackgroundResource(R.drawable.pay_background)
                    return@addTextChangedListener
                }
            }
            payButton.setBackgroundResource(R.drawable.pay_inactive_background)
        }

        payButton.setOnClickListener {
            if (editText.text.isNotEmpty() && !editText.text.equals("0")) {
                payButton.isClickable = false
                startActivity(Intent(this, PaymentActivity::class.java).apply {
                    putExtra(KEY_AMOUNT, editText.text.toString().toInt())
                    putExtra(KEY_PAY_TIMEOUT, viewModel.payTimeOutLiveData.value)
                    putExtra(KEY_MESSAGE_DIALOG, viewModel.messageDialogLiveData.value)
                })
                editText.setText("")
                payButton.isClickable = true
            }
        }


    }
//
//    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
//        if (result.isSuccessful) {
//            val uriContent = result.uriContent
//            viewModel.imagePathLiveData.value = uriContent.toString()
//        }
//    }

//    private fun onAfterPermissionGrant() {
//        cropImage.launch(
//            options{
//                setAspectRatio(1,1)
//            }
//        )
//    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        editText = findViewById(R.id.editText)
        payButton = findViewById(R.id.payButton)
        hiddenButton = findViewById(R.id.hiddenButton)
        payButton.isEnabled = true
    }

//    private fun getPermissions(): Boolean {
//        var requiredPermission = false
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            val permissions = arrayOf(
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//
//            permissions.forEach { permission ->
//                if (PermissionChecker.checkSelfPermission(
//                        this,
//                        permission
//                    ) != PermissionChecker.PERMISSION_GRANTED
//                ) {
//                    requiredPermission = true
//                    Log.d("qqq", "$permission is !!! DENIED !!!")
//                } else {
//                    Log.d("qqq", "$permission is GRANTED")
//                }
//            }
//
//            if (requiredPermission) {
//                requestPermissions(
//                    permissions, 1
//                )
//            }
//        } else {
//            requiredPermission = true
//        }
//
//        return requiredPermission
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (!(grantResults.filter { it != 0 }.size > 0)) {
//            onAfterPermissionGrant()
//        }
//    }


}