package com.example.realm.Utils

import android.content.Context
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration


object DataInit {

    private var instance: DataInit? = null
    private lateinit var realm: Realm
    fun initData(): Realm {
        return realm
    }

    fun getInstance(context: Context): DataInit {
        if (instance == null) {
            synchronized(context) {
                Realm.init(context)
                val config = RealmConfiguration.Builder().name("Record").allowQueriesOnUiThread(true).allowWritesOnUiThread(true).build()
                realm = Realm.getInstance(config)
                instance = DataInit
                Log.i("Tag", "Instance: Create")
            }
        }
        return instance!!
    }

}