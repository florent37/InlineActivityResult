package com.github.florent37.inlineactivityresult.kotlin

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.florent37.inlineactivityresult.InlineActivityResult
import com.github.florent37.inlineactivityresult.request.Request
import com.github.florent37.inlineactivityresult.Result

fun Fragment.startForResult(intent: Intent, block: (Result) -> Unit): KotlinActivityResult {
    return KotlinActivityResult(activity, intent, block)
}

fun FragmentActivity.startForResult(intent: Intent, block: (Result) -> Unit): KotlinActivityResult {
    return KotlinActivityResult(this, intent, block)
}

fun Fragment.startForResult(request: Request, block: (Result) -> Unit): KotlinActivityResult {
    return KotlinActivityResult(activity, request, block)
}

fun FragmentActivity.startForResult(request: Request, block: (Result) -> Unit): KotlinActivityResult {
    return KotlinActivityResult(this, request, block)
}

class KotlinActivityResult {

    constructor(activity: FragmentActivity?, intentToStart: Intent, successBlock: (Result) -> Unit) {
        inlineActivityResult = InlineActivityResult(activity)
                .onSuccess(successBlock)
                .startForResult(intentToStart)
    }

    constructor(activity: FragmentActivity?, request: Request, successBlock: (Result) -> Unit) {
        inlineActivityResult = InlineActivityResult(activity)
                .onSuccess(successBlock)
                .startForResult(request)
    }

    val inlineActivityResult: InlineActivityResult

    fun onFailed(failBlock: ((Result) -> Unit)): KotlinActivityResult {
        inlineActivityResult.onFail(failBlock)
        return this
    }
}
