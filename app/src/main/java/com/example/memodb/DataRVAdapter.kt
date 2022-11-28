package com.example.memodb

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.example.memodb.databinding.ItemMemoBinding

class DataRVAdapter(private val dataList: ArrayList<MemoData>): RecyclerView.Adapter<DataRVAdapter.DataViewHolder>() {
    inner class DataViewHolder(private val viewBinding: ItemMemoBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(data: MemoData) {
            viewBinding.tvContent.text = data.content
            viewBinding.tvNum.text = data.title
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    interface onItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: onItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: onItemClickListener
}