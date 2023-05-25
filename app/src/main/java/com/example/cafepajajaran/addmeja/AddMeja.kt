package com.example.cafepajajaran.addmeja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafepajajaran.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class AddMeja : AppCompatActivity() {
    private lateinit var mejaRecyclerView: RecyclerView
    private lateinit var mejaList: ArrayList<MejaModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meja)

        mejaRecyclerView = findViewById(R.id.rv_meja)
        mejaRecyclerView.layoutManager = LinearLayoutManager(this)
        mejaRecyclerView.setHasFixedSize(true)

        mejaList = arrayListOf<MejaModel>()

        getMejaData()

        var btn_tambah_meja : FloatingActionButton = findViewById(R.id.btn_tambah)

        btn_tambah_meja.setOnClickListener {
            val intent = Intent(this, CreateMeja::class.java)
            startActivity(intent)
        }
    }
    private fun getMejaData(){
        mejaRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Meja")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mejaList.clear()
                if(snapshot.exists()){
                    for(mejaSnap in snapshot.children){
                        val mejaData = mejaSnap.getValue((MejaModel::class.java))
                        mejaList.add(mejaData!!)
                    }
                    val mAdapter = MejaAdpater(mejaList)
                    mejaRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object: MejaAdpater.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@AddMeja, MejaDetailActivity::class.java)

                            intent.putExtra("mejaId", mejaList[position].mejaId)
                            intent.putExtra("mejaName", mejaList[position].mejaName)
                            intent.putExtra("mejaOrang", mejaList[position].mejaOrang)
                            startActivity(intent)
                        }

                    })

                    mejaRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}