package com.vic.villz.artists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vic.villz.topartists.ui.TopArtistsFragment
import dagger.android.AndroidInjection

class MuseleeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, TopArtistsFragment())
            commit()
        }


    }
}
