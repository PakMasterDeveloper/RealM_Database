package com.example.realm.Interfaces

import com.example.realm.Models.UserData


interface DataBase {
    fun insertData(userData: UserData)
    fun readData():List<UserData>
    fun updateData(userData: UserData)
    fun deleteAllData()
    fun deleteOne(id:Int)
    fun readOne(id:Int):UserData
}