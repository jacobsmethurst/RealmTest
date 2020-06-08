package com.example.realmtest

import android.app.Activity
import android.content.Context
import com.example.realmtest.model.Movie
import io.realm.Realm
import io.realm.RealmResults
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class RealmExecutor(var realm: Realm? = appRealm, val ctxt: Context) : Activity() {

    fun executeQuery(sqlMethod: String = "", title: String = "", year: String = ""): String {
        var result = ctxt.getString(R.string.clear_result)
        // do the right SQL method
        when (sqlMethod) {
            "INSERT" -> result = insertMovie(title, year)
            "SELECT" -> result = selectMovies(title, year)
            "UPDATE" -> result = updateMovies(title, year)
            "DELETE" -> result = deleteMovies(title, year)
            "CLEAR" -> realm?.executeTransaction { realm ->
                realm.deleteAll()
            }

        }

        return result

    }

    private fun insertMovie(title: String?, year: String?): String {
        var result: String
        try {
            // all columns must be present to insert
            if (!title?.isEmpty()!! && !year?.isEmpty()!!) {
                realm?.executeTransaction { realm ->
                    val movie = realm.createObject<Movie>(title)
                    movie.year = Integer.parseInt(year)
                }
                result = ctxt.getString(R.string.insert_result, title, year)

            } else {
                result = ctxt.getString(R.string.invalid_input)
            }
        } catch (e: RealmPrimaryKeyConstraintException) {
            // rejects duplicates based on title (primary key)
            // not really realistic i.e. Lion King (1994) & Lion King (2019)
            result = ctxt.getString(R.string.insert_duplicate, title)
        }
        return result
    }

    private fun selectMovies(title: String?, year: String?): String {
        var result: String
        val returned = getQueryResults(title, year)

        if (returned == null) {
            result = ctxt.getString(R.string.select_result, "No movies found matching that query.")

        } else {
            // print results
            val resString = printQueryResults(returned)
            result = ctxt.getString(R.string.select_result, resString)
        }

        return result
    }

    private fun updateMovies(title: String?, year: String?): String {
        return "Not yet implemented"
    }
    private fun deleteMovies(title: String?, year: String?): String {
        var result: String
        val returned = getQueryResults(title, year)

        if (returned == null) {
            result = ctxt.getString(R.string.select_result, "No movies found matching that query.")
        } else {
            val resString = printQueryResults(returned)
            realm?.beginTransaction()
            // delete each row
            for (res in returned) {
                res.deleteFromRealm()
            }
            realm?.commitTransaction()
            // print out the rows that were deleted
            result = ctxt.getString(R.string.delete_result, resString)
        }

        return result
    }

    // used in insert, update, and remove
    // returns a RealmResults containing matching rows
    private fun getQueryResults(title: String?, year: String?): RealmResults<Movie>? {
        var returned: RealmResults<Movie>?

        // both title and year are given
        if (!title?.isEmpty()!! && !year?.isEmpty()!!) {
            returned = realm?.where<Movie>()
                ?.equalTo("title", title)
                ?.equalTo("year", Integer.parseInt(year))
                ?.findAll()
            // only year is given
        } else if (title.isEmpty() && !year?.isEmpty()!!) {
            returned = realm?.where<Movie>()
                ?.equalTo("year", Integer.parseInt(year))
                ?.findAll()
            // only title is given
        } else if (!title.isEmpty() && year?.isEmpty()!!) {
            returned = realm?.where<Movie>()
                ?.equalTo("title", title)
                ?.findAll()
            // empty search
        } else {
            return null;
        }

        return returned;
    }

    // Pretty print the list of rows
    private fun printQueryResults(returned: RealmResults<Movie>): String {
        var resString = ""
        for (res in returned) {
            resString += res.toString()
        }
        return resString
    }
}