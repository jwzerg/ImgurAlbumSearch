package com.imguralbumsearch.album

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.imguralbumsearch.R
import com.imguralbumsearch.databinding.AlbumActivityBinding
import com.imguralbumsearch.rpc.Album

/** The container Activity that holds all pages related to a single album. */
class AlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = AlbumActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Enable the Up button.
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // The Activity should be always launched with an Intent. When the intent is null, crash
        // the app immediately so that we can immediately know we did something wrong in the code.
        val album = checkNotNull(checkNotNull(intent).getParcelableExtra<Album>(EXTRA_ALBUM))

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, AlbumFragment.newInstance(album))
                .commitNow()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val EXTRA_ALBUM = "extra_album"

        // Always use this method to create the Intent from launching AlbumActivity.
        fun createIntent(context: Context, album: Album): Intent {
            return Intent(context, AlbumActivity::class.java).apply {
                putExtra(EXTRA_ALBUM, album)
            }
        }
    }
}