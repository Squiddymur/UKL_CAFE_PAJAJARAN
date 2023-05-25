package com.example.cafepajajaran.addmeja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cafepajajaran.R
import com.google.firebase.database.FirebaseDatabase
import java.sql.RowId

class MejaDetailActivity : AppCompatActivity() {

    private lateinit var tvMejaId: TextView
    private lateinit var tvMejaName: TextView
    private lateinit var tvMejaOrang: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meja_detail)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("mejaId").toString(),
                intent.getStringExtra("mejaName").toString()


            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("mejaId").toString()
            )
        }
    }
    private fun initView(){
        tvMejaId = findViewById(R.id.tvMejaId)
        tvMejaName = findViewById(R.id.tvMejaName)
        tvMejaOrang = findViewById(R.id.tvMejaOrang)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        tvMejaId.text = intent.getStringExtra("mejaId")
        tvMejaName.text = intent.getStringExtra("mejaName")
        tvMejaOrang.text = intent.getStringExtra("mejaOrang")
    }

    private fun openUpdateDialog(
        mejaId: String,
        mejaName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etMejaName = mDialogView.findViewById<TextView>(R.id.etMejaName)
        val etMejaOrang = mDialogView.findViewById<TextView>(R.id.etMejaOrang)
        val btnUpdate = mDialogView.findViewById<TextView>(R.id.btnUpdateData)


        etMejaName.setText(intent.getStringExtra("mejaName").toString())
        etMejaOrang.setText(intent.getStringExtra("mejaOrang").toString())

        mDialog.setTitle("Updating $mejaName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener{
            updatemejaData(
                mejaId,
                etMejaName.text.toString(),
                etMejaOrang.text.toString()
            )
            Toast.makeText(applicationContext, "Meja Data Updateed", Toast.LENGTH_LONG).show()

            tvMejaName.text = etMejaName.text.toString()
            tvMejaOrang.text = etMejaOrang.text.toString()

            alertDialog.dismiss()
        }



    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Meja").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Meja berhasil dihapus", Toast.LENGTH_LONG).show()

            val intent = Intent(this, AddMeja::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Hapus error ${error.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updatemejaData(
        id: String,
        name: String,
        orang: String
    ){
        val dbRef= FirebaseDatabase.getInstance().getReference("Meja").child(id)
        val mejaInfo = MejaModel(id, name, orang)
        dbRef.setValue(mejaInfo)
    }
}