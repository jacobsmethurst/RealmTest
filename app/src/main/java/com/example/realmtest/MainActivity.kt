package com.example.realmtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.EditText
import com.example.realmtest.databinding.ActivityMainBinding

const val METHOD_MESSAGE = "com.example.realmtest.METHOD"
const val TITLE_MESSAGE = "com.example.realmtest.TITLE"
const val YEAR_MESSAGE = "com.example.realmtest.YEAR"
const val INSERT = "INSERT"
const val SELECT = "SELECT"
const val UPDATE = "UPDATE"
const val DELETE = "DELETE"
const val CLEAR = "CLEAR"

class MainActivity() : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var executor: RealmExecutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        executor = RealmExecutor(ctxt = this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return false
    }

    // called after any button is pushed
    fun performAction(view: View) {
        val title = getTitleInput()
        val year = getYearInput()

        val sqlMethod = when (view.id) {
            R.id.selectButton -> SELECT
            R.id.updateButton -> UPDATE
            R.id.deleteButton -> DELETE
            R.id.clearButton -> CLEAR
            else -> INSERT
        }

        binding.results = executor.executeQuery(sqlMethod, title, year)
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