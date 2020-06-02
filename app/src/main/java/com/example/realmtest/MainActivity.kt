package com.example.realmtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.EditText

const val INSERT_MESSAGE = "com.example.realmtest.INSERT"
const val SELECT_MESSAGE = "com.example.realmtest.SELECT"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        // REALM SETUP GOES HERE?
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return false
    }

    fun insertMovie(view: View) {
        // insert movie into Realm db
        Log.d(null,"clicked insert")
        val title = getInput()
        val intent = Intent(this, InsertMovieActivity::class.java).apply {
            putExtra(INSERT_MESSAGE, title)
        }
        startActivity(intent)
    }

    fun selectMovies(view: View) {
        // select movie(s) from Realm db
        Log.d(null, "clicked select")
        val title = getInput()
        val intent = Intent(this, SelectMoviesActivity::class.java).apply {
            putExtra(SELECT_MESSAGE, title)
        }
        startActivity(intent)
    }

    private fun getInput(): String {
        val editText = findViewById<EditText>(R.id.editTextMovieTitle)
        return editText.text.toString()
    }

}