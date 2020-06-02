package com.example.realmtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class RealmActivity(var realm: Realm? = null) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)

        // set up Realm
        val config = RealmConfiguration.Builder()
            .name("movies.realm")
            .build()
        realm = Realm.getInstance(config)
        Log.d(null, "realm path: " + realm?.path)

        // get the movie title that was set
        val sqlMethod = intent.getStringExtra(METHOD_MESSAGE)
        val title = intent.getStringExtra(TITLE_MESSAGE)
        val year = intent.getStringExtra(YEAR_MESSAGE)

        // do the right SQL method
        when (sqlMethod) {
            "INSERT" -> insertMovie(title, year)
            "SELECT" -> selectMovies(title, year)
            "UPDATE" -> updateMovies(title, year)
            "DELETE" -> deleteMovies(title, year)
            "CLEAR" -> realm?.executeTransaction { realm ->
                realm.deleteAll()
                val actionResult = findViewById<TextView>(R.id.resultText).apply {
                    text = getString(R.string.clear_result)
                }
            }

        }
    }

    // rejects duplicates based on title (primary key)
    // not really realistic i.e. Lion King (1994) & Lion King (2019)
    private fun insertMovie(title: String?, year: String?) {
        val result = findViewById<TextView>(R.id.resultText)
        try {
            // all columns must be present
            if (!title?.isEmpty()!! && !year?.isEmpty()!!) {
                realm?.executeTransaction { realm ->
                    val movie = realm.createObject<Movie>(title)
                    movie.year = Integer.parseInt(year)
                }
                result.apply {
                    text = getString(R.string.insert_result, title, year)
                }
            } else {
                result.apply {
                    text = getString(R.string.invalid_input)
                }
            }
        } catch (e: RealmPrimaryKeyConstraintException) {
            result.apply {
                text = getString(R.string.insert_duplicate, title)
            }
        }
    }

    private fun selectMovies(title: String?, year: String?) {
        val result = findViewById<TextView>(R.id.resultText)
        val returned = getQueryResults(title, year)

        if (returned == null) {
            result.apply {
                text = getString(R.string.select_result, "No movies found matching that query.")
            }
        } else {
            result.apply {
                val resString = printQueryResults(returned)
                text = getString(R.string.select_result, resString)
            }
        }

    }

    private fun updateMovies(title: String?, year: String?) {
        val actionResult = findViewById<TextView>(R.id.resultText).apply {
            text = getString(R.string.insert_result, title, year)
        }
    }
    private fun deleteMovies(title: String?, year: String?) {
        val result = findViewById<TextView>(R.id.resultText)
        val returned = getQueryResults(title, year)

        if (returned == null) {
            result.apply {
                text = getString(R.string.select_result, "No movies found matching that query.")
            }
        } else {
            val resString = printQueryResults(returned)
            realm?.beginTransaction()
            for (res in returned) {
                res.deleteFromRealm()
            }
            realm?.commitTransaction()
            result.apply {
                text = getString(R.string.delete_result, resString)
            }
        }
    }

    private fun getQueryResults(title: String?, year: String?): RealmResults<Movie>? {
        var returned: RealmResults<Movie>?

        if (!title?.isEmpty()!! && !year?.isEmpty()!!) {
            returned = realm?.where<Movie>()
                ?.equalTo("title", title)
                ?.equalTo("year", Integer.parseInt(year))
                ?.findAll()
        } else if (title.isEmpty() && !year?.isEmpty()!!) {
            returned = realm?.where<Movie>()
                ?.equalTo("year", Integer.parseInt(year))
                ?.findAll()
        } else if (!title.isEmpty() && year?.isEmpty()!!) {
            returned = realm?.where<Movie>()
                ?.equalTo("title", title)
                ?.findAll()
        } else {
            return null;
        }

        return returned;
    }

    private fun printQueryResults(returned: RealmResults<Movie>): String {
        var resString = ""
        if (returned != null) {
            for (res in returned) {
                resString += res.toString()
            }
        }
        return resString
    }

}