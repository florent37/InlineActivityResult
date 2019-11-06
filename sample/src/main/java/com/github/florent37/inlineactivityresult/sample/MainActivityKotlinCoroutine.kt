package com.github.florent37.inlineactivityresult.sample

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.github.florent37.inlineactivityresult.request.Request
import com.github.florent37.inlineactivityresult.kotlin.InlineActivityResultException
import com.github.florent37.inlineactivityresult.kotlin.coroutines.startForResult
import com.github.florent37.inlineactivityresult.request.RequestFabric
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityKotlinCoroutine : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        requestView.setOnClickListener {
            myMethod(RequestFabric.create(Intent(MediaStore.ACTION_IMAGE_CAPTURE)))
        }

        requestIntentSenderView.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                    val pendingIntent = PendingIntent.getActivity(baseContext, 0, intent, 0)
                    val request = RequestFabric.create(pendingIntent.intentSender, null, 0, 0, 0, null)

                    myMethod(request)
                } catch (e: InlineActivityResultException) {

                }
            }
        }
    }

    fun myMethod(request: Request) = GlobalScope.launch(Dispatchers.Main) {
        try {
            val result = startForResult(request)

            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            resultView.setImageBitmap(imageBitmap)
        } catch (e: InlineActivityResultException) {

        }
    }

}
