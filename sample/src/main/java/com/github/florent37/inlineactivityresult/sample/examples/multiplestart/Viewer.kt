package com.github.florent37.inlineactivityresult.sample.examples.multiplestart

import android.content.ContentResolver
import android.graphics.Bitmap

interface Viewer {

    fun setText(text: String, append: Boolean)

    fun showImage(imageBitmap: Bitmap?, imageViewPos: Int)

    fun getContentResolver(): ContentResolver?

}
