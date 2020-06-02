package com.example.realmtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class InsertMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_movie)

        // get the movie title that was set
        val title = intent.getStringExtra(INSERT_MESSAGE)

        insertMovie(title)
    }

    private fun insertMovie(title: String?) {
        // REALM INSERT GOES HERE
        val actionResult = findViewById<TextView>(R.id.insertResultText).apply {
            text = getString(R.string.insert_result, title)
        }
    }
}