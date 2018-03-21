package com.github.florent37.noactivityresult.sample

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.github.florent37.noactivityresult.kotlin.NoActivityResultException
import com.github.florent37.noactivityresult.kotlin.coroutines.experimental.startForResult
import com.github.florent37.noactivityresult.sample.R
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MainActivityKotlinCoroutine : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        requestView.setOnClickListener {
            myMethod()
        }
    }

    fun myMethod() = launch(UI) {
        try {
            val result = startForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE))

            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            resultView.setImageBitmap(imageBitmap)
        } catch (e: NoActivityResultException) {

        }
    }

}
