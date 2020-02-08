package com.wreckingball.magicdex.network

import com.wreckingball.magicdex.utils.Retryable

class NetworkStatus {
    var status = LOADING
    var message = ""
    lateinit var retryable: Retryable
}