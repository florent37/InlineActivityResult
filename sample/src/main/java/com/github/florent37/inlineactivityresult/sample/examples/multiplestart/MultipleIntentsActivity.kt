package com.github.florent37.inlineactivityresult.sample.examples.multiplestart

import android.content.Intent
import android.os.Bundle
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
import com.github.florent37.inlineactivityresult.sample.examples.common.BaseActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Start 2 Intents in different options: (1 and 2, no dependence in call) and (1 then 2, if 1 is successful)
 */
class MultipleIntentsActivity : BaseActivity(), AbstractMultipleIntentsHolder {

    override val tag: String = "MultipleActivity"

    override val fragmentTag: String = "MULTIPLEFRAGMENT"

    override var textView: TextView? = null
    override var resultView1: ImageView? = null
    override var resultView2: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createView()
    }

    private fun createView() {
        setContentView(R.layout.activity_multiple)

        textView = findViewById(R.id.textView)
        resultView1 = findViewById(R.id.resultView1)
        resultView2 = findViewById(R.id.resultView2)

        val startOneIntent: Button = findViewById(R.id.startOneIntent)
        val startTwoIntents: Button = findViewById(R.id.startTwoIntents)
        val inActivity: CheckBox = findViewById(R.id.inActivity)
        val inFragment: CheckBox = findViewById(R.id.inFragment)
        val startTwoIntentsNested: View = findViewById(R.id.startTwoIntentsNested)
        val startTwoIntentsSeqCor: View = findViewById(R.id.startTwoIntentsSeqCor)

        val firstListener = object : ActivityResultListener {
            override fun onSuccess(result: Result?) {
                StartIntentData.firstOnSuccess(tag, this@MultipleIntentsActivity, result)
            }

            override fun onFailed(result: Result?) {
                StartIntentData.firstOnFail(tag, this@MultipleIntentsActivity, result)
            }
        }

        val secondListener = object : ActivityResultListener {
            override fun onSuccess(result: Result?) {
                StartIntentData.secondOnSuccess(tag, this@MultipleIntentsActivity, result)
            }

            override fun onFailed(result: Result?) {
                StartIntentData.secondOnFail(tag, this@MultipleIntentsActivity, result)
            }
        }

        // start 1
        startOneIntent.setOnClickListener {
            resultView1?.setImageResource(0)
            resultView2?.setImageResource(0)

            if (inActivity.isChecked) {
                setText("starting from activity....", false)

                InlineActivityResult.startForResult(this, StartIntentData.firstIntent, firstListener)
            } else {
                this.openFragment(FragmentOneIntentTask())
            }
        }

        // start 1 and 2
        startTwoIntents.setOnClickListener {
            resultView1?.setImageResource(0)
            resultView2?.setImageResource(0)

            if (inActivity.isChecked) {
                setText("starting from activity....", false)

                InlineActivityResult.startForResult(this, StartIntentData.firstIntent, firstListener)
                InlineActivityResult.startForResult(this, StartIntentData.secondIntent, secondListener)
            } else {
                this.openFragment(FragmentIntentTask())
            }
        }

        // start 1 then 2, if 1 is successful
        startTwoIntentsNested.setOnClickListener {
            resultView1?.setImageResource(0)
            resultView2?.setImageResource(0)

            if (inActivity.isChecked) {
                setText("starting from activity....", false)

                InlineActivityResult.startForResult(this, StartIntentData.firstIntent, object : ActivityResultListener {
                    override fun onSuccess(result: Result?) {
                        StartIntentData.firstOnSuccess(tag, this@MultipleIntentsActivity, result)

                        InlineActivityResult.startForResult(this@MultipleIntentsActivity, StartIntentData.secondIntent, object : ActivityResultListener {
                            override fun onSuccess(result: Result?) {
                                StartIntentData.secondOnSuccess(tag, this@MultipleIntentsActivity, result)
                            }

                            override fun onFailed(result: Result?) {
                                StartIntentData.secondOnFail(tag, this@MultipleIntentsActivity, result)
                            }
                        })
                    }

                    override fun onFailed(result: Result?) {
                        StartIntentData.firstOnFail(tag, this@MultipleIntentsActivity, result)
                    }

                })
            } else {
                this.openFragment(FragmentIntentTaskNested())
            }
        }

        // start 1 and 2 in coroutine scope
        startTwoIntentsSeqCor.setOnClickListener {
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

        this.startForResult(firstIntent) { result ->
            StartIntentData.firstOnSuccess(tag, this@MultipleIntentsActivity, result)
        }.onFailed { result ->
            StartIntentData.firstOnFail(tag, this@MultipleIntentsActivity, result)
        }

        delay(1000)

        this.startForResult(secondIntent) { result ->
            StartIntentData.secondOnSuccess(tag, this@MultipleIntentsActivity, result)
        }.onFailed { result ->
            StartIntentData.secondOnFail(tag, this@MultipleIntentsActivity, result)
        }

    }

}