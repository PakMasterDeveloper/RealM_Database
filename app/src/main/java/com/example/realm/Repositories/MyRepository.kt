package com.example.realm.Repositories

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.realm.Activities.MainActivity
import com.example.realm.Interfaces.DataBase
import com.example.realm.Models.UserData
import com.example.realm.Utils.DataInit
import io.realm.RealmResults

class MyRepository private constructor(context: Context):DataBase{
    private val context: Context
    init {
        DataInit.getInstance(context)
        this.context=context
    }
    companion object{
        @SuppressLint("StaticFieldLeak")
        private var instance:MyRepository?=null
        fun getInstance(context: Context):MyRepository{
            if(instance==null)
            {
                instance= MyRepository(context)
            }
            return instance!!
        }
    }

    override fun insertData(userData: UserData) {
        val num:Number?=DataInit.initData().where(UserData::class.java).max("ID")
        var id:Int=0
        id = if(num==null) {
            0
        } else {
            num.toInt()+1
        }
        try {
            DataInit.initData().executeTransaction {
                DataInit.initData().copyToRealm(UserData(id,userData.Student_ID,userData.Student_Name,userData.Student_Class,userData.Student_age,userData.Student_Image))
                Toast.makeText(context,"Data Inserted!",Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context,MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        }
        catch (e:Exception){
            Toast.makeText(context,"Error: Data not Insert ${e.localizedMessage}",Toast.LENGTH_LONG).show()
        }
    }

    override fun readData(): List<UserData> {
        return DataInit.initData().where(UserData::class.java).findAll()
    }

    override fun updateData( userData: UserData) {
        val user:UserData=DataInit.initData().where(UserData::class.java).equalTo("ID",userData.ID).findFirst()!!
        DataInit.initData().executeTransaction {
            user.Student_ID=userData.Student_ID
            user.Student_Name=userData.Student_Name
            user.Student_Class=userData.Student_Class
            user.Student_age=userData.Student_age
            user.Student_Image=userData.Student_Image
            DataInit.initData().copyFromRealm(user)
            Toast.makeText(context,"Data Updated!",Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(context,MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun deleteAllData() {
        DataInit.initData().executeTransaction {
            val query: RealmResults<UserData> = DataInit.initData().where(UserData::class.java).findAll()
            query.deleteAllFromRealm()
        }
    }

    override fun deleteOne(id: Int) {
        val user:UserData=DataInit.initData().where(UserData::class.java).equalTo("ID",id).findFirst()!!
        DataInit.initData().executeTransaction {
            user.deleteFromRealm()
        }
    }

    override fun readOne(id: Int):UserData {
        return DataInit.initData().where(UserData::class.java).equalTo("ID",id).findFirst()!!
    }
}