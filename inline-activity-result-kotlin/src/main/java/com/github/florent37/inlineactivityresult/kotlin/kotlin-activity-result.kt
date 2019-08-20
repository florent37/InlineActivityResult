package com.github.florent37.inlineactivityresult.kotlin

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.florent37.inlineactivityresult.InlineActivityResult
import com.github.florent37.inlineactivityresult.Result

fun Fragment.startForResult(intent: Intent, block: (Result) -> Unit): KotlinActivityResult {
    return KotlinActivityResult(activity, intent, block)
}

fun FragmentActivity.startForResult(intent: Intent, block: (Result) -> Unit): KotlinActivityResult {
    return KotlinActivityResult(this, intent, block)
}

class KotlinActivityResult(activity: FragmentActivity?, intentToStart: Intent, successBlock: (Result) -> Unit) {

    val inlineActivityResult: InlineActivityResult

    init {
        inlineActivityResult = InlineActivityResult(activity)
                .onSuccess(successBlock)
                .startForResult(intentToStart)
    }

    fun onFailed(failBlock: ((Result) -> Unit)): KotlinActivityResult {
        inlineActivityResult.onFail(failBlock)
        return this
    }
}
