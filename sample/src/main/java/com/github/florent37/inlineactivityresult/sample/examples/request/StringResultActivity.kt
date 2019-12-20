package com.github.florent37.inlineactivityresult.sample.examples.request

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Return string as result
 */
class StringResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val resultIntent = Intent()

        resultIntent.putExtra(REQUEST_KEY, "[$this]")

        setResult(Activity.RESULT_OK, resultIntent)

        finish()
    }

    companion object {
        const val REQUEST_KEY: String = "SRA_KEY"
    }

}