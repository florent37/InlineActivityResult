package com.github.florent37.inlineactivityresult.sample.examples.multiplestart.fragment

import com.github.florent37.inlineactivityresult.InlineActivityResult
import com.github.florent37.inlineactivityresult.Result
import com.github.florent37.inlineactivityresult.callbacks.ActivityResultListener
import com.github.florent37.inlineactivityresult.sample.examples.multiplestart.StartIntentData

/**
 * Example of fragment, start First and Second intent
 */
open class FragmentIntentTask : TwoIntentsFragment() {

    override val fragmentTag: String = "FragmentIntentTask"

    override fun startIntent() {
        resultView1?.setImageResource(0)
        resultView2?.setImageResource(0)

        setText("starting from fragment....", false)

        InlineActivityResult.startForResult(this, StartIntentData.firstIntent, object : ActivityResultListener {
            override fun onSuccess(result: Result?) {
                StartIntentData.firstOnSuccess(fragmentTag, this@FragmentIntentTask, result)
            }

            override fun onFailed(result: Result?) {
                StartIntentData.firstOnFail(fragmentTag, this@FragmentIntentTask, result)
            }
        })

        InlineActivityResult.startForResult(this, StartIntentData.secondIntent, object : ActivityResultListener {
            override fun onSuccess(result: Result?) {
                StartIntentData.secondOnSuccess(fragmentTag, this@FragmentIntentTask, result)
            }

            override fun onFailed(result: Result?) {
                StartIntentData.secondOnFail(fragmentTag, this@FragmentIntentTask, result)
            }
        })
    }

}
