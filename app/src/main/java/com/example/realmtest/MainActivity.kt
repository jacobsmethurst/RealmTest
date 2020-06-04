package com.example.realmtest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.EditText

const val METHOD_MESSAGE = "com.example.realmtest.METHOD"
const val TITLE_MESSAGE = "com.example.realmtest.TITLE"
const val YEAR_MESSAGE = "com.example.realmtest.YEAR"
const val INSERT = "INSERT"
const val SELECT = "SELECT"
const val UPDATE = "UPDATE"
const val DELETE = "DELETE"
const val CLEAR = "CLEAR"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return false
    }

    // called after any button is pushed
    fun performAction(view: View) {
        val title = getTitleInput()
        val year = getYearInput()

        val intent = Intent(this, RealmActivity::class.java).apply {
            putExtra(TITLE_MESSAGE, title)
            putExtra(YEAR_MESSAGE, year)
        }

        val sqlMethod = when (view.id) {
            R.id.selectButton -> SELECT
            R.id.updateButton -> UPDATE
            R.id.deleteButton -> DELETE
            R.id.clearButton -> CLEAR
            else -> INSERT
        }
        intent.apply {
            putExtra(METHOD_MESSAGE, sqlMethod)
        }
        // a different implementation could work in the same activity, or have different activities
        // for each method. Not really sure which is best for this app
        startActivity(intent)
    }

    private fun getTitleInput(): String {
        val editText = findViewById<EditText>(R.id.editTextMovieTitle)
        return editText.text.toString();
    }

    private fun getYearInput(): String {
        val editText = findViewById<EditText>(R.id.editTextMovieYear)
        return editText.text.toString()
    }

}