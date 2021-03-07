package com.imguralbumsearch.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imguralbumsearch.R
import com.imguralbumsearch.databinding.HomeActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/** The container Activity that holds the home page. */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }
}