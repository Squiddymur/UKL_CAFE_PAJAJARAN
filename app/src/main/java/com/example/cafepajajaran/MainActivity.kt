package com.example.cafepajajaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cafepajajaran.addmeja.AddMeja
import com.example.cafepajajaran.addmenu.AddMenu

class MainActivity : AppCompatActivity() {

    private lateinit var btnAddMeja : Button
    private lateinit var btnAddMenu : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnAddMeja = findViewById(R.id.btnMeja)
        btnAddMenu = findViewById(R.id.btnMenu)

        btnAddMeja.setOnClickListener {
            val intent = Intent(this, AddMeja::class.java)
            startActivity(intent)
        }
        btnAddMenu.setOnClickListener {
            val intent = Intent(this, AddMenu::class.java)
            startActivity(intent)
        }
    }
}