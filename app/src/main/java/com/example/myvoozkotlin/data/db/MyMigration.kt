package com.example.myvoozkotlin.data.db

import android.util.Log
import io.realm.DynamicRealm
import io.realm.RealmMigration
import java.util.*

class MyMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var oldVersion = oldVersion
        val schema = realm.schema
        Log.e("   --SCHEMA VERSION--", oldVersion.toString())

//        if (oldVersion == 1L) {
//            val LessonSchema = schema.create("LessonDbModel")
//                .addField("name", String::class.java)
//                .addField("typeName", String::class.java)
//                .addField("classroom", String::class.java)
//                .addField("teacher", String::class.java)
//                .addField("firstTime", String::class.java)
//                .addField("lastTime", String::class.java)
//                .addField("number", Int::class.java)
//            oldVersion++
//        }
    }
}