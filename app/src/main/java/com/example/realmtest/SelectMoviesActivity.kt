package com.example.realmtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SelectMoviesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_movies)

        // get the movie title that was set
        val title = intent.getStringExtra(SELECT_MESSAGE)

        selectMovies(title)
    }

    private fun selectMovies(title: String?) {
        // REALM SELECT GOES HERE
        val actionResult = findViewById<TextView>(R.id.selectResultText).apply {
            text = getString(R.string.select_result, title)
        }
    }
}