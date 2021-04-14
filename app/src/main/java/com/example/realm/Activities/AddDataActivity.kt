package com.example.realm.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.realm.Models.UserData
import com.example.realm.ViewModels.CustomViewModel
import com.example.realm.databinding.ActivityAddDataBinding
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class AddDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDataBinding
    private lateinit var customViewModel: CustomViewModel
    private  val REQUEST_CODE_GALLERY:Int=999;
    private  val  REQUEST_CODE_CAMERA:Int=1;
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customViewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(CustomViewModel::class.java)
        with(binding){
            val key=intent.getStringExtra("Key")
            val id=intent.getIntExtra("id",0)
            if(key=="Update"){
                val use:UserData=customViewModel.readOneData(id)
                IDText.setText(use.Student_ID)
                NameText.setText(use.Student_Name)
                ClassText.setText(use.Student_Class)
                AgeText.setText(use.Student_age)
                myImage.setImageBitmap(convert(use.Student_Image))
                InsertButton.text = "Update"
            }
            InsertButton.setOnClickListener {
                if(key=="Update"){
                    val myUser=UserData(id,IDText.text.toString(),NameText.text.toString(),ClassText.text.toString(),AgeText.text.toString(),convert())
                    customViewModel.updateData(myUser)
                }
                else{
                    val user= UserData(0,IDText.text.toString(),NameText.text.toString(),ClassText.text.toString(),AgeText.text.toString(),convert())
                    customViewModel.insertData(user)
                }

            }
            myImage.setOnClickListener {
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, REQUEST_CODE_GALLERY)
            }
            myImage.setOnLongClickListener {
                val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
                return@setOnLongClickListener false
            }
        }
    }
    private fun convert():ByteArray{
         val bitmap:Bitmap=(binding.myImage.drawable as BitmapDrawable).bitmap;
         val byteArrayOutputStream=ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        val bytes:ByteArray=byteArrayOutputStream.toByteArray();
        return bytes;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQUEST_CODE_GALLERY)
        {
            if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                 val intent=Intent(Intent.ACTION_PICK);
                intent.type = "image/*";
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else
            {
                Toast.makeText(this, "You don't have permission to access ", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK)
        {
            if(data?.data !=null)
            {
                try {
                    val inputStream:InputStream?= contentResolver.openInputStream(data.data!!)
                    val bitmap:Bitmap= BitmapFactory.decodeStream(inputStream)
                    binding.myImage.setImageBitmap(bitmap)
                }
                catch (e:Exception ) {
                    e.printStackTrace()
                }
            }
            else
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
        if(requestCode==REQUEST_CODE_CAMERA )
        {
            val bitmap:Bitmap = data?.extras?.get("data") as Bitmap
            binding.myImage.setImageBitmap(bitmap);
        }
    }
    private fun convert(image:ByteArray?):Bitmap{
        val byteArrayInputStream= ByteArrayInputStream(image);
        val bitmap:Bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
        return bitmap;
    }
}