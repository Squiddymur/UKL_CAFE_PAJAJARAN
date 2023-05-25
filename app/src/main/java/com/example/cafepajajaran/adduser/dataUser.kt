package com.example.cafepajajaran.adduser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.cafepajajaran.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class dataUser : AppCompatActivity() {
    private lateinit var emailRecyclerView: RecyclerView
    private lateinit var rvLoadingData: TextView
    private lateinit var listEmail:ArrayList<EmailModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_user)

        emailRecyclerView = findViewById(R.id.rvEmail)
        emailRecyclerView.layoutManager = LinearLayoutManager(this)
        emailRecyclerView.setHasFixedSize(true)
        rvLoadingData = findViewById(R.id.tv_loading_data)

        listEmail = arrayListOf<EmailModel>()
        getEmailData()

        var btn_tambah_email : FloatingActionButton = findViewById(R.id.tambahEmail)

        btn_tambah_email.setOnClickListener {
            val intent = Intent(this,AddUser::class.java)
            startActivity(intent)
        }
    }

    private fun getEmailData() {
        emailRecyclerView.visibility = View.GONE
        rvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Email")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listEmail.clear()
                if (snapshot.exists()) {
                    for (emailSnap in snapshot.children){
                        val emailData = emailSnap.getValue(EmailModel::class.java)
                        listEmail.add(emailData!!)
                    }
                    val mAdapter = emailAdapter(listEmail)
                    emailRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : emailAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@dataUser, DetailUser::class.java)
                            intent.putExtra("emailId", listEmail[position].emailId)
                            intent.putExtra("email", listEmail[position].email)
                            startActivity(intent)
                        }
                    })
                    emailRecyclerView.visibility = View.VISIBLE
                    rvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}