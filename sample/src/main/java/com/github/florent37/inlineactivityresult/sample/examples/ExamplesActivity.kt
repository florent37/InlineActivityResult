package com.github.florent37.inlineactivityresult.sample.examples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.florent37.inlineactivityresult.sample.examples.multiplestart.TwoIntendsExample
import com.github.florent37.runtimepermission.RuntimePermission

class ExamplesActivity : AppCompatActivity() {

    private val tag: String = "ExamplesActivity"

    private val fragmentTag: String = "EXAMPLEFRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e(tag, "onCreate $this")

        TwoIntendsExample(this, tag).onCreate()
    }

    override fun onResume() {
        super.onResume()

        Log.e(tag, "onResume $this")
    }

    override fun onPause() {
        super.onPause()

        Log.e(tag, "onPause $this")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e(tag, "onDestroy $this")
    }

    override fun onBackPressed() {
        val visibleFragments = supportFragmentManager.fragments.filter { it.tag.equals(fragmentTag) && it.isVisible }

        if (visibleFragments.isEmpty()) {
            super.onBackPressed()
        } else {
            visibleFragments.forEach { removeFragment(it) }
        }
    }

    fun openFragment(fragment: Fragment) {
        RuntimePermission.askPermission(this)
                .onAccepted {
                    setFragment(fragment)
                }
                .ask()
    }

    private fun setFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            replace(android.R.id.content, fragment, fragmentTag)

            commit()
        }
    }

    private fun removeFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            remove(fragment)

            commit()
        }
    }

}



