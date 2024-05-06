package com.example.chaintechnetworktask.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chaintechnetworktask.DataSource.Models.AccountDetailsModel
import com.example.chaintechnetworktask.databinding.SavePasswordItemsBinding

class PasswordAdapter(var context: Context, var savePassList: ArrayList<AccountDetailsModel>) :

    RecyclerView.Adapter<PasswordAdapter.viewHolder>() {


    inner class viewHolder(var binding: SavePasswordItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordAdapter.viewHolder {
        val binding =
            SavePasswordItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordAdapter.viewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return savePassList.size
    }
}