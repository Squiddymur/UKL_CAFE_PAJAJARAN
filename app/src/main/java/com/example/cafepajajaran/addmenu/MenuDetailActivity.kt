package com.example.cafepajajaran.addmenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cafepajajaran.R
import com.example.cafepajajaran.addmeja.AddMeja
import com.example.cafepajajaran.addmeja.MejaModel
import com.google.firebase.database.FirebaseDatabase

class MenuDetailActivity : AppCompatActivity() {

    private lateinit var tvMenuId: TextView
    private lateinit var tvMenuName: TextView
    private lateinit var tvMenuHarga: TextView
    private lateinit var tvMenuDesc: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("menuId").toString(),
                intent.getStringExtra("menuName").toString()


            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("menuId").toString()
            )
        }
    }
    private fun initView(){
        tvMenuId = findViewById(R.id.tvMenuId)
        tvMenuName = findViewById(R.id.tvMenuName)
        tvMenuHarga = findViewById(R.id.tvMenuHarga)
        tvMenuDesc = findViewById(R.id.tvMenuDesc)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        tvMenuId.text = intent.getStringExtra("menuId")
        tvMenuName.text = intent.getStringExtra("menuName")
        tvMenuHarga.text = intent.getStringExtra("menuHarga")
        tvMenuDesc.text = intent.getStringExtra("menuDesc")
    }

    private fun openUpdateDialog(
        menuId: String,
        menuName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog_menu, null)

        mDialog.setView(mDialogView)

        val etMenuName = mDialogView.findViewById<TextView>(R.id.etMenuName)
        val etMenuHarga = mDialogView.findViewById<TextView>(R.id.etMenuHarga)
        val etMenuDesc = mDialogView.findViewById<TextView>(R.id.etMenuDesc)
        val btnUpdate = mDialogView.findViewById<TextView>(R.id.btnUpdateData)


        etMenuName.setText(intent.getStringExtra("menuName").toString())
        etMenuHarga.setText(intent.getStringExtra("menuHarga").toString())
        etMenuDesc.setText(intent.getStringExtra("menuDesc").toString())

        mDialog.setTitle("Updating $menuName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener{
            updatemejaData(
                menuId,
                etMenuName.text.toString(),
                etMenuHarga.text.toString(),
                etMenuDesc.text.toString()
            )
            Toast.makeText(applicationContext, "Menu Data Updateed", Toast.LENGTH_LONG).show()

            tvMenuName.text = etMenuName.text.toString()
            tvMenuHarga.text = etMenuHarga.text.toString()
            tvMenuDesc.text = etMenuDesc.text.toString()

            alertDialog.dismiss()
        }



    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Menu berhasil dihapus", Toast.LENGTH_LONG).show()

            val intent = Intent(this, AddMenu::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Hapus error ${error.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updatemejaData(
        id: String,
        name: String,
        harga: String,
        desc: String
    ){
        val dbRef= FirebaseDatabase.getInstance().getReference("Menu").child(id)
        val mejaInfo = MenuModel(id, name, harga, desc)
        dbRef.setValue(mejaInfo)
    }
}