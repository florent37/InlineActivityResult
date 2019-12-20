package com.github.florent37.inlineactivityresult.sample.examples.request

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.florent37.inlineactivityresult.kotlin.startForResult

/**
 * start [StringResultActivity] and return string as result
 */
class ProxyResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val request = Intent(this, StringResultActivity::class.java)

        startForResult(request) { result ->
            val resultString = result.data?.getStringExtra(StringResultActivity.REQUEST_KEY)

            val resultIntent = Intent()

            resultIntent.putExtra(REQUEST_KEY, "$resultString from [$this]")

            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        }.onFailed { result ->
            throw result.cause?: IllegalStateException()
        }
    }

    companion object {
        const val REQUEST_KEY: String = "PRA_KEY"
    }

}