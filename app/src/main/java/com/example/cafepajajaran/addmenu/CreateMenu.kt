package com.example.cafepajajaran.addmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cafepajajaran.R
import com.example.cafepajajaran.addmeja.MejaModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateMenu : AppCompatActivity() {

    private lateinit var create_menu: EditText
    private lateinit var create_harga: EditText
    private lateinit var create_desc: EditText
    private lateinit var btn_simpan: Button

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_menu)

        create_menu = findViewById(R.id.create_menu)
        create_harga = findViewById(R.id.create_harga)
        create_desc = findViewById(R.id.create_desc)
        btn_simpan = findViewById(R.id.btn_simpan)
        database = FirebaseDatabase.getInstance().getReference("Menu")

        btn_simpan.setOnClickListener{
            savemenu()
        }
    }
    private fun savemenu() {
        val menuName = create_menu.text.toString()
        val menuHarga = create_harga.text.toString()
        val menuDesc = create_desc.text.toString()

        if(menuName.isEmpty()){
            create_menu.error="Masukka Menu"
        }
        if(menuHarga.isEmpty()){
            create_harga.error="Masukka Harga"
        }
        if(menuDesc.isEmpty()){
            create_menu.error="Masukka Deskripsi"
        }
        val menuId = database.push().key!!

        val menu = MenuModel(menuId, menuName, menuHarga, menuDesc)

        database.child(menuId).setValue(menu)
            .addOnCompleteListener{
                Toast.makeText(this, "Menu berhasil ditambahkan", Toast.LENGTH_LONG).show()

                create_menu.text.clear()
                create_harga.text.clear()
                create_desc.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}