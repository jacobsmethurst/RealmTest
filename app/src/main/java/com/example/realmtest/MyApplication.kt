package com.example.realmtest

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

// not sure if this is the best way to expose this
var appRealm: Realm? = null

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Realm configuration
        // https://realm.io/docs/kotlin/latest/#configuring-a-realm
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("movies.realm")
            .build()
        appRealm = Realm.getInstance(config)
    }
}