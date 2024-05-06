package com.example.chaintechnetworktask.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chaintechnetworktask.DataSource.Models.AccountDetailsModel
import com.example.chaintechnetworktask.databinding.SavePasswordItemsBinding

class PasswordAdapter(
    val onClickItem: ((AccountDetailsModel?) -> Unit)?,
    val context: Context,

) :

    RecyclerView.Adapter<PasswordAdapter.viewHolder>() {

    var diffUtil = object : DiffUtil.ItemCallback<AccountDetailsModel>() {
        override fun areItemsTheSame(
            oldItem: AccountDetailsModel, newItem: AccountDetailsModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AccountDetailsModel, newItem: AccountDetailsModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    inner class viewHolder(var binding: SavePasswordItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val passwordSaved = differ.currentList[position]
//            val model = savePassList[position]
            binding.apply {
                accountName.text = passwordSaved.accountName
                password.text = passwordSaved.password

                //click a single items
                binding.cardLayout.setOnClickListener {
                    onClickItem?.invoke(passwordSaved)
                }
            }
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
        return differ.currentList.size
    }
}