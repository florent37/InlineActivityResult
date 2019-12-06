package com.github.florent37.inlineactivityresult.sample.examples.multiplestart

import com.github.florent37.inlineactivityresult.InlineActivityResult
import com.github.florent37.inlineactivityresult.Result
import com.github.florent37.inlineactivityresult.callbacks.ActivityResultListener

/**
 * Example of fragment, start one intent
 */
open class FragmentOneIntentTask : TwoIntentsFragment() {

    override val fragmentTag: String = "FragmentOneIntentTask"

    override fun startIntent() {
        resultView1?.setImageResource(0)
        resultView2?.setImageResource(0)

        setText("starting from fragment....", false)

        InlineActivityResult.startForResult(this, StartIntentData.firstIntent, object : ActivityResultListener {
            override fun onSuccess(result: Result?) {
                StartIntentData.firstOnSuccess(fragmentTag, this@FragmentOneIntentTask, result)
            }

            override fun onFailed(result: Result?) {
                StartIntentData.firstOnFail(fragmentTag, this@FragmentOneIntentTask, result)
            }
        })
    }

}
