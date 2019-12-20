package com.github.florent37.inlineactivityresult.sample.examples.standardbehavior

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.florent37.inlineactivityresult.sample.R
import com.github.florent37.inlineactivityresult.sample.examples.common.BaseActivity

/**
 * Start standard startActivityForResult() method
 */
class StandardActivity : BaseActivity() {

    private val REQUEST_CODE: Int = 1003

    override val tag: String = "StandardActivity"

    override val fragmentTag: String = "STANDARDFRAGMENT"

    private var textView: TextView? = null
    var resultView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createView()
    }

    private fun createView() {
        setContentView(R.layout.activity_standard)

        textView = findViewById(R.id.textView)

        resultView = findViewById(R.id.resultView)

        findViewById<View>(R.id.runAction).setOnClickListener {
            textView?.text = ""
            resultView?.setImageResource(0)

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(intent, REQUEST_CODE)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            Log.e(tag, "resultCode $resultCode, $data")

            if (resultCode == Activity.RESULT_OK) {
                textView?.text = "OK resultCode $resultCode"

                val extras = data?.extras

                val imageBitmap = extras?.getParcelable<Bitmap?>("data")

                imageBitmap?.also { bitmap ->
                    resultView?.setImageBitmap(bitmap)
                }
            } else {
                textView?.text = "FAIL resultCode $resultCode"
            }
        }
    }

}