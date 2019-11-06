package com.github.florent37.inlineactivityresult.sample

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.github.florent37.inlineactivityresult.request.Request
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.github.florent37.inlineactivityresult.request.RequestFabric
import kotlinx.android.synthetic.main.activity_request.*

class MainActivityKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        requestView.setOnClickListener {
            myMethod(RequestFabric.create(Intent(MediaStore.ACTION_IMAGE_CAPTURE)))
        }

        requestIntentSenderView.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            val pendingIntent = PendingIntent.getActivity(baseContext, 0, intent, 0)

            val request = RequestFabric.create(pendingIntent.intentSender, null, 0, 0, 0, null)

            myMethod(request)
        }
    }

    fun myMethod(request: Request) {
        startForResult(request) { result ->
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            resultView.setImageBitmap(imageBitmap)
        }.onFailed { result ->

        }
    }
}
