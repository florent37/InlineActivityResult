package com.github.florent37.inlineactivityresult.sample.examples.multiplestart

import android.content.ContentResolver
import android.content.Intent
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.github.florent37.inlineactivityresult.InlineActivityResult
import com.github.florent37.inlineactivityresult.Result
import com.github.florent37.inlineactivityresult.callbacks.ActivityResultListener
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.github.florent37.inlineactivityresult.sample.R
import com.github.florent37.inlineactivityresult.sample.examples.ExamplesActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Start 2 Intends in different options: (1 and 2, no dependence in call) and (1 then 2, if 1 is successful)
 */
class TwoIntendsExample(private val activity: ExamplesActivity, val tag: String) : AbstractMultipleIntendsHolder {

    override var textView: TextView? = null
    override var resultView1: ImageView? = null
    override var resultView2: ImageView? = null
    override fun getContentResolver(): ContentResolver? {
        return activity.contentResolver
    }

    fun onCreate() {
        activity.setContentView(R.layout.activity_example)

        textView = activity.findViewById(R.id.textView)
        resultView1 = activity.findViewById(R.id.resultView1)
        resultView2 = activity.findViewById(R.id.resultView2)

        val startTwoIntends: Button = activity.findViewById(R.id.startTwoIntends)
        val inActivity: CheckBox = activity.findViewById(R.id.inActivity)
        val inFragment: CheckBox = activity.findViewById(R.id.inFragment)
        val startTwoIntendsNested: View = activity.findViewById(R.id.startTwoIntendsNested)
        val startTwoIntendsSeqCor: View = activity.findViewById(R.id.startTwoIntendsSeqCor)

        val firstListener = object : ActivityResultListener {
            override fun onSuccess(result: Result?) {
                StartIntentData.firstOnSuccess(tag, this@TwoIntendsExample, result)
            }

            override fun onFailed(result: Result?) {
                StartIntentData.firstOnFail(tag, this@TwoIntendsExample, result)
            }
        }

        val secondListener = object : ActivityResultListener {
            override fun onSuccess(result: Result?) {
                StartIntentData.secondOnSuccess(tag, this@TwoIntendsExample, result)
            }

            override fun onFailed(result: Result?) {
                StartIntentData.secondOnFail(tag, this@TwoIntendsExample, result)
            }
        }

        // start 1 and 2
        startTwoIntends.setOnClickListener {
            resultView1?.setImageResource(0)
            resultView2?.setImageResource(0)

            if (inActivity.isChecked) {
                setText("starting from activity....", false)

                InlineActivityResult.startForResult(activity, StartIntentData.firstIntent, firstListener)
                InlineActivityResult.startForResult(activity, StartIntentData.secondIntent, secondListener)
            } else {
                activity.openFragment(FragmentIntentTask())
            }
        }

        // start 1 then 2, if 1 is successful
        startTwoIntendsNested.setOnClickListener {
            resultView1?.setImageResource(0)
            resultView2?.setImageResource(0)

            if (inActivity.isChecked) {
                setText("starting from activity....", false)

                InlineActivityResult.startForResult(activity, StartIntentData.firstIntent, object : ActivityResultListener {
                    override fun onSuccess(result: Result?) {
                        StartIntentData.firstOnSuccess(tag, this@TwoIntendsExample, result)

                        InlineActivityResult.startForResult(activity, StartIntentData.secondIntent, object : ActivityResultListener {
                            override fun onSuccess(result: Result?) {
                                StartIntentData.secondOnSuccess(tag, this@TwoIntendsExample, result)
                            }

                            override fun onFailed(result: Result?) {
                                StartIntentData.secondOnFail(tag, this@TwoIntendsExample, result)
                            }
                        })
                    }

                    override fun onFailed(result: Result?) {
                        StartIntentData.firstOnFail(tag, this@TwoIntendsExample, result)
                    }

                })
            } else {
                activity.openFragment(FragmentIntentTaskNested())
            }
        }

        // start 1 and 2 in coroutine scope
        startTwoIntendsSeqCor.setOnClickListener {
            setText("starting from activity....", false)

            resultView1?.setImageResource(0)
            resultView2?.setImageResource(0)

            GlobalScope.launch {
                test()
            }
        }

        // checkbox, where to run intent (in activity)
        inActivity.setOnCheckedChangeListener { _, isChecked ->
            inFragment.isChecked = !isChecked
        }

        // checkbox, where to run intent (in fragment)
        inFragment.setOnCheckedChangeListener { _, isChecked ->
            inActivity.isChecked = !isChecked
        }
    }

    private suspend fun test() {
        val firstIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val secondIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        activity.startForResult(firstIntent) { result ->
            StartIntentData.firstOnSuccess(tag, this@TwoIntendsExample, result)
        }.onFailed { result ->
            StartIntentData.firstOnFail(tag, this@TwoIntendsExample, result)
        }

        delay(1000)

        activity.startForResult(secondIntent) { result ->
            StartIntentData.secondOnSuccess(tag, this@TwoIntendsExample, result)
        }.onFailed { result ->
            StartIntentData.secondOnFail(tag, this@TwoIntendsExample, result)
        }

    }

}