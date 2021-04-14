package com.example.realm.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.realm.Models.UserData
import com.example.realm.Repositories.MyRepository

class CustomViewModel(application: Application):AndroidViewModel(application) {
    private val myRepository:MyRepository by lazy {
        MyRepository.getInstance(application)
    }
    private val myLis:MutableLiveData<List<UserData>> by lazy {
        MutableLiveData()
    }
    fun insertData(userData: UserData){
        myRepository.insertData(userData)
    }
    fun readAll():LiveData<List<UserData>>{
        myLis.value=myRepository.readData()
        return myLis
    }
    fun updateData(userData: UserData){
        myRepository.updateData(userData)
    }
    fun deleteData(){
        myRepository.deleteAllData()
    }
    fun deleteOne(id:Int){
        myRepository.deleteOne(id)
    }
    fun readOneData(id:Int):UserData{
        return myRepository.readOne(id)
    }
}