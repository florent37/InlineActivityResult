package com.github.florent37.inlineactivityresult.kotlin

import android.content.Intent
import com.github.florent37.inlineactivityresult.InlineActivityResult
import com.github.florent37.inlineactivityresult.Result
import java.lang.Exception

class InlineActivityResultException(val result: Result) : Exception() {

    val data: Intent?
    val requestCode: Int
    val resultCode: Int
    val inlineActivityResult: InlineActivityResult

    init {
        data = result.data
        requestCode = result.requestCode
        resultCode = result.resultCode
        inlineActivityResult = result.inlineActivityResult
    }

}