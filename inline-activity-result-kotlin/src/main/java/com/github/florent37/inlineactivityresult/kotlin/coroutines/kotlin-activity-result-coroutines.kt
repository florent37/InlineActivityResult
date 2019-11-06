package com.github.florent37.inlineactivityresult.kotlin.coroutines

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.florent37.inlineactivityresult.InlineActivityResult
import com.github.florent37.inlineactivityresult.request.Request
import com.github.florent37.inlineactivityresult.Result
import com.github.florent37.inlineactivityresult.kotlin.InlineActivityException
import com.github.florent37.inlineactivityresult.kotlin.InlineActivityResultException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun FragmentActivity.startForResult(intent: Intent): Result = suspendCoroutine { continuation ->
    var resumed = false
    InlineActivityResult(this)
            .onSuccess { result ->
                if (!resumed) {
                    resumed = true
                    continuation.resume(result)
                }
            }
            .onFail { result ->
                if (!resumed) {
                    resumed = true
                    continuation.resumeWithException(InlineActivityResultException(result))
                }
            }
            .startForResult(intent)
}

suspend fun Fragment.startForResult(intent: Intent): Result = suspendCoroutine { continuation ->
    var resumed = false
    when (activity) {
        null -> continuation.resumeWithException(InlineActivityException())
        else -> InlineActivityResult(activity)
                .onSuccess { result ->
                    if (!resumed) {
                        resumed = true
                        continuation.resume(result)
                    }
                }
                .onFail { result ->
                    if (!resumed) {
                        resumed = true
                        continuation.resumeWithException(InlineActivityResultException(result))
                    }
                }
                .startForResult(intent)
    }
}

suspend fun FragmentActivity.startForResult(request: Request): Result = suspendCoroutine { continuation ->
    var resumed = false
    InlineActivityResult(this)
            .onSuccess { result ->
                if (!resumed) {
                    resumed = true
                    continuation.resume(result)
                }
            }
            .onFail { result ->
                if (!resumed) {
                    resumed = true
                    continuation.resumeWithException(InlineActivityResultException(result))
                }
            }
            .startForResult(request)
}

suspend fun Fragment.startForResult(request: Request): Result = suspendCoroutine { continuation ->
    var resumed = false
    when (activity) {
        null -> continuation.resumeWithException(InlineActivityException())
        else -> InlineActivityResult(activity)
                .onSuccess { result ->
                    if (!resumed) {
                        resumed = true
                        continuation.resume(result)
                    }
                }
                .onFail { result ->
                    if (!resumed) {
                        resumed = true
                        continuation.resumeWithException(InlineActivityResultException(result))
                    }
                }
                .startForResult(request)
    }
}

