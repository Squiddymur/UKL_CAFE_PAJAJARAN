package com.example.cafepajajaran.addmenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafepajajaran.R
import com.example.cafepajajaran.addmeja.CreateMeja
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class AddMenu : AppCompatActivity() {

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuList: ArrayList<MenuModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        menuRecyclerView = findViewById(R.id.rv_menu)
        menuRecyclerView.layoutManager = LinearLayoutManager(this)
        menuRecyclerView.setHasFixedSize(true)

        menuList = arrayListOf<MenuModel>()

        getMenuData()

        var btn_tambah_menu : FloatingActionButton = findViewById(R.id.btn_tambah)

        btn_tambah_menu.setOnClickListener {
            val intent = Intent(this, CreateMenu::class.java)
            startActivity(intent)
        }
    }
    private fun getMenuData(){
        menuRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Menu")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuList.clear()
                if(snapshot.exists()){
                    for(menuSnap in snapshot.children){
                        val menuData = menuSnap.getValue((MenuModel::class.java))
                        menuList.add(menuData!!)
                    }
                    val mAdapter = MenuAdapter(menuList)
                    menuRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object: MenuAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@AddMenu, MenuDetailActivity::class.java)

                            intent.putExtra("menuId", menuList[position].menuId)
                            intent.putExtra("menuName", menuList[position].menuName)
                            intent.putExtra("menuHarga", menuList[position].menuHarga)
                            intent.putExtra("menuDesc", menuList[position].menuDesc)
                            startActivity(intent)
                        }

                    })

                    menuRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}