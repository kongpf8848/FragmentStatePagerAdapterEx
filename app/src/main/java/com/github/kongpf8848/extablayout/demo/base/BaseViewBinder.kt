package com.github.kongpf8848.extablayout.demo.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.drakeet.multitype.ItemViewBinder

abstract class BaseViewBinder<T,VH:RecyclerView.ViewHolder> constructor(): ItemViewBinder<T,VH>() {

    var listener:OnItemClickListener<T>?=null;

    constructor(listener:OnItemClickListener<T>):this(){
        this.listener=listener;
    }


    override fun onBindViewHolder(holder: VH, data: T) {
       if(this.listener!=null){
           holder.itemView.apply {
               setOnClickListener {
                   listener!!.onItemClick(holder.adapterPosition,data)
               }
               setOnLongClickListener{
                   listener!!.onItemLongClick(holder.adapterPosition,data)
                    true
               }
           }
       }
    }

    fun inflate(inflater: LayoutInflater,resource:Int,root:ViewGroup?):View=inflater.inflate(resource,root,false)


    interface OnItemClickListener<T> {
        fun onItemClick(position: Int, data: T)
        fun onItemLongClick(position: Int, data:T){}

    }
}