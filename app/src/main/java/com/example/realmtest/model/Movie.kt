package com.example.realmtest.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Movie(
    @PrimaryKey var title: String? = "",
    var year: Int = 0
): RealmObject() {
    override fun toString(): String {
        return "\n" + this.title + " (" + year + ")"
    }
}
