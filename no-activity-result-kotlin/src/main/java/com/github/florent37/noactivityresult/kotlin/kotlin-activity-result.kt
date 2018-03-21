package com.github.florent37.noactivityresult.kotlin

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.github.florent37.noactivityresult.NoActivityResult
import com.github.florent37.noactivityresult.Result

fun Fragment.startForResult(intent: Intent, block: (Result) -> Unit): KotlinActivityResult {
    return KotlinActivityResult(activity, intent, block)
}

fun FragmentActivity.startForResult(intent: Intent, block: (Result) -> Unit): KotlinActivityResult {
    return KotlinActivityResult(this, intent, block)
}

class KotlinActivityResult(activity: FragmentActivity?, intentToStart: Intent, successBlock: (Result) -> Unit) {

    val noActivityResult: NoActivityResult

    init {
        noActivityResult = NoActivityResult(activity)
                .onSuccess(successBlock)
                .startForResult(intentToStart)
    }

    fun onFailed(failBlock: ((Result) -> Unit)): KotlinActivityResult {
        noActivityResult.onFail(failBlock)
        return this
    }
}
