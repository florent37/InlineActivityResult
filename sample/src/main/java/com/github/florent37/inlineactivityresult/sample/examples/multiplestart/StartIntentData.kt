package com.github.florent37.inlineactivityresult.sample.examples.multiplestart

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Log
import com.github.florent37.inlineactivityresult.Result

/**
 * Info about intends and listeners
 */
object StartIntentData {

    val firstIntent: Intent
        get() = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    val secondIntent: Intent
        get() = Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT), "Select Image")
                .putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)))

    val firstOnSuccess: (tag: String, viewer: Viewer, result: Result?) -> Unit = { tag, viewer, result ->
        Log.i(tag, "First resultCode ${result?.resultCode}, ${result?.data}")

        viewer.setText("|First OK, resultCode ${result?.resultCode}|", true)

        val extras = result?.data?.extras

        val imageBitmap = extras?.getParcelable<Bitmap?>("data")

        viewer.showImage(imageBitmap, 1)
    }

    val firstOnFail: (tag: String, viewer: Viewer, result: Result?) -> Unit = { tag, viewer, result ->
        result?.cause?.printStackTrace()

        Log.i(tag, "First resultCode ${result?.resultCode}, ${result?.data}")

        viewer.setText("|First Fail resultCode ${result?.resultCode}, Intent ${result?.data}|", true)
    }

    val secondOnSuccess: (tag: String, viewer: Viewer, result: Result?) -> Unit = { tag, viewer, result ->
        Log.i(tag, "Second resultCode ${result?.resultCode}, ${result?.data}")

        viewer.setText("|Second OK, resultCode ${result?.resultCode}|", true)

        val selectedImage = result?.data?.data

        if (selectedImage != null) {
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = viewer.getContentResolver()?.query(selectedImage, filePathColumn, null, null, null)

            if (cursor != null) {
                cursor.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val picturePath = cursor.getString(columnIndex)
                cursor.close()

                val imageBitmap = BitmapFactory.decodeFile(picturePath)

                viewer.showImage(imageBitmap, 2)
            }
        }
    }

    val secondOnFail: (tag: String, viewer: Viewer, result: Result?) -> Unit = { tag, viewer, result ->
        result?.cause?.printStackTrace()

        Log.i(tag, "Second resultCode ${result?.resultCode}, ${result?.data}")

        viewer.setText("|Second Fail resultCode ${result?.resultCode}, Intent ${result?.data}|", true)
    }

}