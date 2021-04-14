package com.example.realm.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realm.Activities.AddDataActivity
import com.example.realm.Models.UserData
import com.example.realm.ViewHolders.CustomViewHolder
import com.example.realm.ViewModels.CustomViewModel
import com.example.realm.databinding.CustomRowBinding
import io.realm.OrderedRealmCollection
import java.io.ByteArrayInputStream

class CustomAdapter(private val context: Context, private var arrayList: OrderedRealmCollection<UserData>, private var customViewModel: CustomViewModel
):RecyclerView.Adapter<CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding=CustomRowBinding.inflate(LayoutInflater.from(context),parent,false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.cusImage.setImageBitmap(convert(arrayList[position].Student_Image))
        holder.binding.CusNameText.text=arrayList[position].Student_Name
        holder.binding.CusAgeText.text=arrayList[position].Student_age
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context,AddDataActivity::class.java).putExtra("Key","Update").putExtra("id",position))
        }
    }

    override fun getItemCount(): Int =arrayList.size
    private fun convert(image:ByteArray?):Bitmap{
        val byteArrayInputStream= ByteArrayInputStream(image);
        val bitmap:Bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
        return bitmap;
    }
   fun deleteData(index:Int){
       customViewModel.deleteOne(index)
       notifyDataSetChanged()
   }
}