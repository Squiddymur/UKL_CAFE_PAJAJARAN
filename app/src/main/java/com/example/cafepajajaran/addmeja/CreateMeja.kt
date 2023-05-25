package com.example.cafepajajaran.addmeja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cafepajajaran.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateMeja : AppCompatActivity() {
    private lateinit var create_meja: EditText
    private lateinit var create_orang: EditText
    private lateinit var btn_simpan: Button

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meja)

        create_meja = findViewById(R.id.create_meja)
        create_orang = findViewById(R.id.create_orang)
        btn_simpan = findViewById(R.id.btn_simpan)
        database = FirebaseDatabase.getInstance().getReference("Meja")

        btn_simpan.setOnClickListener{
            savemeja()
        }
    }

    private fun savemeja() {
        val mejaName = create_meja.text.toString()
        val mejaOrang = create_orang.text.toString()

        if(mejaName.isEmpty()){
            create_meja.error="Masukka nomor meja"
        }
        if(mejaOrang.isEmpty()){
            create_meja.error="Masukka banyak orang"
        }
        val mejaId = database.push().key!!

        val meja = MejaModel(mejaId, mejaName, mejaOrang)

        database.child(mejaId).setValue(meja)
            .addOnCompleteListener{
                Toast.makeText(this, "Meja berhasil ditambahkan", Toast.LENGTH_LONG).show()

                create_meja.text.clear()
                create_orang.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}