package com.github.florent37.inlineactivityresult.sample.examples.multiplestart

import android.content.ContentResolver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.florent37.inlineactivityresult.sample.R

/**
 * Abstract class, that start 2 intents
 */
abstract class TwoIntentsFragment : Fragment(), AbstractMultipleIntentsHolder {

    abstract val fragmentTag: String

    override var textView: TextView? = null

    override var resultView1: ImageView? = null
    override var resultView2: ImageView? = null

    override fun getContentResolver(): ContentResolver? {
        return activity?.applicationContext?.contentResolver
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_example, container, false)

        textView = root.findViewById(R.id.textView)

        resultView1 = root.findViewById(R.id.resultView1)
        resultView2 = root.findViewById(R.id.resultView2)

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

    abstract fun startIntent()
}
