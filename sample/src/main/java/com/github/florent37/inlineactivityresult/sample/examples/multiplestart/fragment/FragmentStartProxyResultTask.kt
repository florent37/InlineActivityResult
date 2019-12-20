package com.github.florent37.inlineactivityresult.sample.examples.multiplestart.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.github.florent37.inlineactivityresult.sample.R
import com.github.florent37.inlineactivityresult.sample.examples.request.ProxyResultActivity

/**
 * Example of fragment, start [ProxyResultActivity] for result
 */
class FragmentStartProxyResultTask : Fragment() {

    private var textView: TextView? = null

    val fragmentTag: String = "FragmentProxyTask"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_example, container, false)

        textView = root.findViewById(R.id.textView)

        setText(fragmentTag, false)

        val runIntent: Button = root.findViewById(R.id.runIntent)

        runIntent.setOnClickListener {
            startIntent()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null) {
            startIntent()
        }
    }

    @SuppressLint("SetTextI18n")
    fun setText(text: String, append: Boolean) {
        if (append) {
            textView?.text = (textView?.text ?: "") as String + " $text"
        } else {
            textView?.text = text
        }
    }

    private fun startIntent() {

        setText("starting ProxyResultActivity from fragment....", false)

        startForResult(Intent(this.context, ProxyResultActivity::class.java)) { result ->
            val resultString = result.data?.getStringExtra(ProxyResultActivity.REQUEST_KEY)

            setText("|StingResultFromProxy OK resultCode ${result.resultCode}, resultString $resultString|", true)
        }.onFailed { result ->
            result.cause?.printStackTrace()

            Log.i(tag, "First resultCode ${result.resultCode}, ${result.data}")

            setText("|StingResultFromProxy Fail resultCode ${result.resultCode}, Intent ${result.data}|", true)
        }
    }

}
