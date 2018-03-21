package com.github.florent37.noactivityresult.sample

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.github.florent37.noactivityresult.kotlin.startForResult
import kotlinx.android.synthetic.main.activity_request.*

class MainActivityKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        requestView.setOnClickListener {
            myMethod()
        }
    }

    fun myMethod() {
        startForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE)) { result ->
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            resultView.setImageBitmap(imageBitmap)
        }.onFailed { result ->

        }
    }
}