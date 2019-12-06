package com.github.florent37.inlineactivityresult.sample.examples.common

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.florent37.runtimepermission.RuntimePermission

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val tag: String

    protected abstract val fragmentTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e(tag, "onCreate $this")
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

    fun open(classActivity: Class<*>) {
        startActivity(Intent(this, classActivity))
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
