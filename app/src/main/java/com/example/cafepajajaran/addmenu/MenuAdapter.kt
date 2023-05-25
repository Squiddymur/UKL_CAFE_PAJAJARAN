package com.example.cafepajajaran.addmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cafepajajaran.R

class MenuAdapter(private val menuList: ArrayList<MenuModel>):
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.row_menu, parent, false)
        return ViewHolder(itemView, mListener)
    }
    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        val currentMenu = menuList[position]
        holder.tvMenuName.text = currentMenu.menuName
        holder.tvMenuHarga.text = currentMenu.menuHarga
        holder.tvMenuDesc.text = currentMenu.menuDesc
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val tvMenuName : TextView = itemView.findViewById(R.id.tv_menu_nama)
        val tvMenuHarga : TextView = itemView.findViewById(R.id.tv_menu_harga)
        val tvMenuDesc : TextView = itemView.findViewById(R.id.tv_menu_deskripsi)
        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(absoluteAdapterPosition)
            }

        }
    }

}