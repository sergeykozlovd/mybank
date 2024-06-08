package com.examples.paycoin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var loginLiveData = MutableLiveData<String>()
    var merchantIdLiveData = MutableLiveData<String>()
    var btcLiveData = MutableLiveData<String>()
    var payTimeOutLiveData = MutableLiveData(0)
    var messageDialogLiveData = MutableLiveData<String>()
    var imagePathLiveData = MutableLiveData<String>()
}