package com.examples.paycoin

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.RingtoneManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class PaymentActivity:AppCompatActivity(R.layout.activity_payment) {

//    private  lateinit var ivCardAnim:ImageView
    private  lateinit var toolbar: Toolbar
    private  lateinit var tvAmount: TextView
    private lateinit var textConnecting:TextView
    private var amount = 0
    private var timeout = 0
    private var messageDialog = ""
    private var isSuccess = true

    private var isPlayAnimate = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getParam()
        initViews()
        initAnimation()
        initListeners()
        paymentDone()
    }

    private fun initAnimation(){


        ObjectAnimator.ofFloat(textConnecting, "alpha",0f, 1f).apply {
            duration = 700
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }


    private fun paymentDone(){
        Handler(Looper.getMainLooper()).postDelayed({
            isPlayAnimate = false
            if (!isFinishing) {
                showDoneDialog()
            }
        },(timeout * 1000).toLong())
    }

    private fun showDoneDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_payment_done)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            Toolbar.LayoutParams.MATCH_PARENT,
            Toolbar.LayoutParams.WRAP_CONTENT
        )
        dialog.findViewById<TextView>(R.id.tvMessageDialog).setText(messageDialog)
        dialog.findViewById<ImageView>(R.id.imageView).setImageResource( if (isSuccess) R.drawable.ic_payment_success else R.drawable.ic_payment_failure)
        dialog.findViewById<Button>(R.id.button).setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
        try {

            RingtoneManager.getRingtone(
                applicationContext,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            ).play()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getParam(){
        amount = intent.getIntExtra(KEY_AMOUNT,0)
        timeout = intent.getIntExtra(KEY_PAY_TIMEOUT,0)
        messageDialog = intent.getStringExtra(KEY_MESSAGE_DIALOG).toString()
        isSuccess = intent.getBooleanExtra(KEY_IS_SUCCESS,true)
    }

    private fun initListeners(){
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun  initViews(){
        textConnecting = findViewById(R.id.textConnection)
        toolbar = findViewById(R.id.toolbar)
        tvAmount = findViewById(R.id.tvAmount)
        tvAmount.text = "Pay Amount ${amount}"
    }

}