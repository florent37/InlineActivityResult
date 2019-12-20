package com.github.florent37.inlineactivityresult.sample.examples.multiplestart

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView

@SuppressLint("SetTextI18n")
interface AbstractMultipleIntentsHolder : Viewer {

    var textView: TextView?

    var resultView1: ImageView?
    var resultView2: ImageView?

    override fun setText(text: String, append: Boolean) {
        if (append) {
            textView?.text = (textView?.text ?: "") as String + " $text"
        } else {
            textView?.text = text
        }
    }

    override fun showImage(imageBitmap: Bitmap?, imageViewPos: Int) {
        imageBitmap?.also { bitmap ->
            when (imageViewPos) {
                1 -> resultView1?.setImageBitmap(bitmap)
                2 -> resultView2?.setImageBitmap(bitmap)
            }
        }
    }

}