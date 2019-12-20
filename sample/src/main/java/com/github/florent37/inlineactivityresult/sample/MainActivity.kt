package com.github.florent37.inlineactivityresult.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.florent37.inlineactivityresult.sample.examples.ExamplesActivity
import com.github.florent37.inlineactivityresult.sample.fragment.*
import com.github.florent37.runtimepermission.RuntimePermission
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Sample of a very basic activity asking for permission.
 * It shows a button to trigger the permission dialog if permission is needed,
 * and hide it when it doesn't
 */
class MainActivity : AppCompatActivity() {

    private val fragmentTag: String = "TESTFRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.fragments.any { it.tag.equals(fragmentTag) && it.isAdded }) {
            menu.visibility = View.GONE
        }

        commonTestActivity.setOnClickListener {
            open(ExamplesActivity::class.java)
        }

        rxActivity.setOnClickListener {
            open(MainActivityRx::class.java)
        }

        rxFragment.setOnClickListener {
            openFragment(MainFragmentRx())
        }

        java7Activity.setOnClickListener {
            open(MainActivityJava7::class.java)
        }

        java7Fragment.setOnClickListener {
            openFragment(MainFragmentJava7())
        }

        java8Activity.setOnClickListener {
            open(MainActivityJava8::class.java)
        }

        java8Fragment.setOnClickListener {
            openFragment(MainFragmentJava8())
        }

        kotlinActivity.setOnClickListener {
            open(MainActivityKotlin::class.java)
        }

        kotlinFragment.setOnClickListener {
            openFragment(MainFragmentKotlin())
        }

        kotlinCoroutineActivity.setOnClickListener {
            open(MainActivityKotlinCoroutine::class.java)
        }

        kotlinCoroutineFragment.setOnClickListener {
            openFragment(MainFragmentKotlinCoroutine())
        }

    }

    override fun onBackPressed() {
        val visibleFragments = supportFragmentManager.fragments.filter { it.tag.equals(fragmentTag) && it.isVisible }

        if (visibleFragments.isEmpty()) {
            super.onBackPressed()
        } else {
            visibleFragments.forEach { removeFragment(it) }

            menu.visibility = View.VISIBLE
        }
    }

    private fun open(classActivity: Class<*>) {
        RuntimePermission.askPermission(this)
                .onAccepted {
                    startActivity(Intent(this, classActivity))
                }
                .ask()
    }

    private fun openFragment(fragment: Fragment) {
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

        menu.visibility = View.GONE
    }

    private fun removeFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            remove(fragment)

            commit()
        }
    }

}
