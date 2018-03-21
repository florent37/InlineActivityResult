package com.github.florent37.noactivityresult.kotlin

import android.content.Intent
import com.github.florent37.noactivityresult.NoActivityResult
import com.github.florent37.noactivityresult.Result
import java.lang.Exception

class NoActivityResultException(val result: Result) : Exception() {

    val data: Intent?
    val requestCode: Int
    val resultCode: Int
    val noActivityResult: NoActivityResult

    init {
        data = result.data
        requestCode = result.requestCode
        resultCode = result.resultCode
        noActivityResult = result.noActivityResult
    }

}