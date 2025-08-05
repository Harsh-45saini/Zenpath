package com.example.zenpath.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zenpath.data.local.PrefManager

open class BaseActivity : AppCompatActivity() {
    var prefManager: PrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefManager = PrefManager(this@BaseActivity)
    }
}