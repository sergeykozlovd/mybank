package com.examples.paycoin

import android.animation.ObjectAnimator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

enum class SoundType{
    SUCCESS,
    FAILURE,
    SCAN_FINISH
}
class PaymentActivity : AppCompatActivity(R.layout.activity_payment) {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var toolbar: Toolbar
    private lateinit var tvAmount: TextView
    private lateinit var textStatus: TextView
    private var amount = 0
    private var timeout = 0
    private var messageDialog = ""
    private var isSuccess = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getParam()
        initViews()
        initAnimation()
        initListeners()
        paymentDone()
    }

    private fun playSound(soundType: SoundType){
            mediaPlayer = MediaPlayer.create(
                this,
                when (soundType){
                    SoundType.SUCCESS -> R.raw.success
                    SoundType.FAILURE -> R.raw.failure
                    SoundType.SCAN_FINISH -> R.raw.click
                }
            )
            mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun initAnimation() {
        ObjectAnimator.ofFloat(textStatus, "alpha", 0f, 1f).apply {
            duration = 700
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }


    private fun paymentDone() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (!isFinishing) {
                textStatus.text = getString(R.string.connecting)
                playSound(SoundType.SCAN_FINISH)
                Handler(Looper.getMainLooper()).postDelayed({
                    showDoneDialog()
                    playSound(if (isSuccess) SoundType.SUCCESS else SoundType.FAILURE )
                }, 2000)
            }
        }, (timeout * 1000).toLong())
    }

    private fun showDoneDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_payment_done)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            Toolbar.LayoutParams.MATCH_PARENT,
            Toolbar.LayoutParams.WRAP_CONTENT
        )
        dialog.findViewById<TextView>(R.id.tvMessageDialog).setText(messageDialog)
        dialog.findViewById<ImageView>(R.id.imageView)
            .setImageResource(if (isSuccess) R.drawable.ic_payment_success else R.drawable.ic_payment_failure)
        dialog.findViewById<Button>(R.id.button).setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

    private fun getParam() {
        amount = intent.getIntExtra(KEY_AMOUNT, 0)
        timeout = intent.getIntExtra(KEY_PAY_TIMEOUT, 0)
        messageDialog = intent.getStringExtra(KEY_MESSAGE_DIALOG).toString()
        isSuccess = intent.getBooleanExtra(KEY_IS_SUCCESS, true)
    }

    private fun initListeners() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        tvAmount = findViewById(R.id.tvAmount)
        textStatus = findViewById(R.id.textStatus)
        tvAmount.text = "Pay Amount ${amount}"
    }

}