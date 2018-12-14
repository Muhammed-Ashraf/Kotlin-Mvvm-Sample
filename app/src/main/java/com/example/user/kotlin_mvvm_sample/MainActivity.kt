package com.example.user.kotlin_mvvm_sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.user.kotlin_mvvm_sample.ui.apicall.ApiCallFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ApiCallFragment.newInstance())
                .commitNow()
        }
    }

}
