# RealM_Database
# Method for of Realm Database
//            Realm.init(this)
//            val name:String="Record"
//            val config=RealmConfiguration.Builder().name(name).allowQueriesOnUiThread(true).allowWritesOnUiThread(true).build()
//            val realm:Realm= Realm.getInstance(config)
//                //First manual method
//                try {
//                    realm.beginTransaction()
//                    val student:Student=realm.createObject(Student::class.java,IDText.text.toString().toInt())
//                    student.Student_Name
//                    student.Student_Age=NameText.text.toString()
//                    student.Student_Age=AgeText.text.toString()
//                    realm.commitTransaction()
//                }
//                catch (e:Exception){
//                    e.printStackTrace()
//                    realm.commitTransaction()
//                }
//                //Second automatic Method
//                realm.executeTransaction {
//
//                    //First Technique
//                    val student:Student=realm.createObject(Student::class.java,IDText.text.toString().toInt())
//                    student.Student_Name
//                    student.Student_Age=NameText.text.toString()
//                    student.Student_Age=AgeText.text.toString()
//
//                    //Second Technique
//                    realm.copyToRealm(Student(IDText.text.toString().toInt(),NameText.text.toString(),AgeText.text.toString()))
//
//                    // Third Technique
//                    val arrayList= listOf(UserData(IDText.text.toString().toInt(),NameText.text.toString(),AgeText.text.toString()))
//                    realm.insert(arrayList)
//                    Toast.makeText(this@MainActivity,"Insert Value! ",Toast.LENGTH_LONG).show()
//
//                    //Read Data from Database
//                    //between("Student_ID",1,5) this method use to filter data from database
//                    // equalTo("Student_Name","Danish") this method use for filter data by using name
//                    //limit(3) this method use for limitation apply on database
//                    //sort("Student_ID",Sort.DESCENDING) this  method use for sorting dat in database
//                     val query:RealmQuery<UserData> = realm.where(UserData::class.java).sort("Student_ID",Sort.DESCENDING)
//                    val result:RealmResults<UserData> = query.findAll()
//                    for (i in 0 until result.size){
//                        Log.i("Tag","Data : ${result[i]?.Student_Name}")
//                    }
//
//                    //Update Data from Database
//                    val dat: UserData? =realm.where(UserData::class.java).equalTo("Student_Name","danish").findFirst()
//                    dat?.Student_Name="Hussain"
//                    dat?.Student_age="34"
//
//                    //Delete Record from Database
//
//                    //Single Record Delete from Database
//                    val dat: UserData? =realm.where(UserData::class.java).equalTo("Student_Name","danish").findFirst()
//                    dat?.deleteFromRealm()
//
//                    //Multiple Record Delete from Database
//                    val query:RealmResults<UserData> = realm.where(UserData::class.java).between("Student_ID",2,6).findAll()
//                    val query:RealmResults<UserData> = realm.where(UserData::class.java).findAll()
//                    query.deleteAllFromRealm()
//                }
//            }
# Classpath
  classpath "io.realm:realm-gradle-plugin:10.4.0"
# Plugin
  id 'realm-android'
