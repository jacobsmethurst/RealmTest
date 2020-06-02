package com.example.realmtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.lang.Exception

const val METHOD_MESSAGE = "com.example.realmtest.METHOD"
const val TITLE_MESSAGE = "com.example.realmtest.TITLE"
const val YEAR_MESSAGE = "com.example.realmtest.YEAR"
const val VIEW_MESSAGE = "com.example.realmtest.VIEW"
const val INSERT = "INSERT"
const val SELECT = "SELECT"
const val UPDATE = "UPDATE"
const val DELETE = "DELETE"
const val CLEAR = "CLEAR"

open class Movie(
    @PrimaryKey var title: String? = "",
    var year: Int = 0
): RealmObject() {
    override fun toString(): String {
        return "\n" + this.title + " (" + year + ")"
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        Realm.init(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return false
    }

    fun performAction(view: View) {
        // insert movie into Realm db
        Log.d(null,"clicked insert")

//        try {
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
            startActivity(intent)
//        } catch (e: Exception) { // would make this more robust in a real app :)
//            if (view.id == R.id.clearButton) {
//                val intent = Intent(this, RealmActivity::class.java).apply {
//                    putExtra(TITLE_MESSAGE, "")
//                    putExtra(YEAR_MESSAGE, 0)
//                    putExtra(METHOD_MESSAGE, CLEAR)
//                }
//                startActivity(intent)
//            } else {
//                Log.e(null, e.message, e)
//                val invalidText = findViewById<TextView>(R.id.invalidText)
//                invalidText.visibility = View.VISIBLE
//            }
//        }

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