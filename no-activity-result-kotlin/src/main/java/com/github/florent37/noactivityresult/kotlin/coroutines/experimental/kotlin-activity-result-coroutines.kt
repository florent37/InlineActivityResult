package com.github.florent37.noactivityresult.kotlin.coroutines.experimental

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.github.florent37.noactivityresult.NoActivityResult
import com.github.florent37.noactivityresult.Result
import com.github.florent37.noactivityresult.kotlin.NoActivityException
import com.github.florent37.noactivityresult.kotlin.NoActivityResultException
import kotlin.coroutines.experimental.suspendCoroutine

suspend fun FragmentActivity.startForResult(intent: Intent): Result = suspendCoroutine { continuation ->
    NoActivityResult(this)
            .onSuccess { result ->
                continuation.resume(result)
            }
            .onFail { result ->
                continuation.resumeWithException(NoActivityResultException(result))
            }
            .startForResult(intent)
}

suspend fun Fragment.startForResult(intent: Intent): Result = suspendCoroutine { continuation ->
    when (activity) {
        null -> continuation.resumeWithException(NoActivityException())
        else -> NoActivityResult(activity)
                .onSuccess { result ->
                    continuation.resume(result)
                }
                .onFail { result ->
                    continuation.resumeWithException(NoActivityResultException(result))
                }
                .startForResult(intent)
    }
}

