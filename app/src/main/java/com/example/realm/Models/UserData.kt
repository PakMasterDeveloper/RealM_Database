package com.example.realm.Models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.io.Serializable

open class UserData(
    @PrimaryKey
    var ID:Int?=null,
    @Required
    var Student_ID: String? = null,
    @Required
    var Student_Name: String? = null,
    @Required
    var Student_Class: String? = null,
    @Required
    var Student_age: String? = null,
    @Required
    var Student_Image:ByteArray?=null
) : RealmObject()