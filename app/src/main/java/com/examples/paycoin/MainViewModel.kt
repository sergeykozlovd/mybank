package com.examples.paycoin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var loginLiveData = MutableLiveData<String>()
    var merchantIdLiveData = MutableLiveData<String>()
    var btcLiveData = MutableLiveData<String>()
    var payTimeOut1LiveData = MutableLiveData(0)
    var payTimeOut2LiveData = MutableLiveData(0)
    var payTimeOut3LiveData = MutableLiveData(0)
    var switchSuccess1LiveData = MutableLiveData(true)
    var switchSuccess2LiveData = MutableLiveData(true)
    var switchSuccess3LiveData = MutableLiveData(true)

    var messageSuccessDialogLiveData = MutableLiveData<String>()
    var messageFailureDialogLiveData = MutableLiveData<String>()
    var imagePathLiveData = MutableLiveData<String>()

    var runCount = 1
}