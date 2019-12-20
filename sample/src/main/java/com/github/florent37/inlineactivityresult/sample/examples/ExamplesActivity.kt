package com.github.florent37.inlineactivityresult.sample.examples

import android.os.Bundle
import android.view.View
import com.github.florent37.inlineactivityresult.sample.R
import com.github.florent37.inlineactivityresult.sample.examples.common.BaseActivity
import com.github.florent37.inlineactivityresult.sample.examples.multiplestart.MultipleIntentsActivity
import com.github.florent37.inlineactivityresult.sample.examples.standardbehavior.StandardActivity

class ExamplesActivity : BaseActivity() {

    override val tag: String = "ExamplesActivity"

    override val fragmentTag: String = "EXAMPLEFRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_example)

        findViewById<View>(R.id.startNormalActivity).setOnClickListener {
            open(StandardActivity::class.java)
        }

        findViewById<View>(R.id.startMultipleActivity).setOnClickListener {
            open(MultipleIntentsActivity::class.java)
        }
    }


}



