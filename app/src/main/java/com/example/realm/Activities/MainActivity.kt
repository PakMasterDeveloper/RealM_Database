package com.example.realm.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realm.Adapters.CustomAdapter
import com.example.realm.Models.UserData
import com.example.realm.ViewModels.CustomViewModel
import com.example.realm.databinding.ActivityMainBinding
import io.realm.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var customViewModel: CustomViewModel
    private lateinit var customAdapter:CustomAdapter
    private lateinit var list: List<UserData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customViewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(CustomViewModel::class.java)
        list=ArrayList()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        with(binding){
            customViewModel.readAll().observe(this@MainActivity, androidx.lifecycle.Observer{
                if (it!=null){
                    customAdapter= CustomAdapter(this@MainActivity,it as OrderedRealmCollection<UserData>,customViewModel)
                    myRecycler.apply {
                        setHasFixedSize(true)
                        layoutManager=LinearLayoutManager(this@MainActivity)
                        adapter=customAdapter
                    }
                    val item: ItemTouchHelper.SimpleCallback =object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
                        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                          customAdapter.deleteData(viewHolder.adapterPosition)
                        }
                    }
                    val itemTouchHelper = ItemTouchHelper(item)
                    itemTouchHelper.attachToRecyclerView(myRecycler)
                }
                else{
                    Toast.makeText(this@MainActivity,"List is empty",Toast.LENGTH_SHORT).show()
                }
            })

            AddData.setOnClickListener {
                startActivity(Intent(this@MainActivity,AddDataActivity::class.java))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==android.R.id.home)
        {
            customViewModel.deleteData()
            customAdapter.notifyDataSetChanged()
            return true;
        }
        return super.onOptionsItemSelected(item)
    }
}