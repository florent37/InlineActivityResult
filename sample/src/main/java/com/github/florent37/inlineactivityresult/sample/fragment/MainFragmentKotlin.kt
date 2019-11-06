package com.github.florent37.inlineactivityresult.sample.fragment

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.florent37.inlineactivityresult.request.Request
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.github.florent37.inlineactivityresult.request.RequestFabric
import com.github.florent37.inlineactivityresult.sample.R
import kotlinx.android.synthetic.main.activity_request.*

class MainFragmentKotlin : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.activity_request, null)

        root.findViewById<View>(R.id.requestView).setOnClickListener {
            myMethod(RequestFabric.create(Intent(MediaStore.ACTION_IMAGE_CAPTURE)))
        }

        root.findViewById<View>(R.id.requestIntentSenderView).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val request = RequestFabric.create(pendingIntent.intentSender, null, 0, 0, 0, null)

            myMethod(request)
        }

        return root
    }

    fun myMethod(request: Request) {
        startForResult(request) { result ->
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            resultView.setImageBitmap(imageBitmap)
        }.onFailed { result ->

        }
    }

}
