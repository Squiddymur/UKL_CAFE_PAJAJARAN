package com.example.cafepajajaran.addmeja


import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.cafepajajaran.R

class MejaAdpater(private val mejaList: ArrayList<MejaModel>):
    RecyclerView.Adapter<MejaAdpater.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.row_meja, parent, false)
        return ViewHolder(itemView, mListener)
    }
    override fun onBindViewHolder(holder: MejaAdpater.ViewHolder, position: Int) {
        val currentMeja = mejaList[position]
        holder.tvMejaName.text = currentMeja.mejaName
    }

    override fun getItemCount(): Int {
        return mejaList.size
    }
    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val tvMejaName : TextView = itemView.findViewById(R.id.tv_nomor_meja)
        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(absoluteAdapterPosition)
            }

        }
    }

}