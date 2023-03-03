package com.example.myapplication.RecylerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Repository.DogEntity
import com.example.myapplication.databinding.RecylerImageBinding

class MainViewHolder(val binding: RecylerImageBinding) : RecyclerView.ViewHolder(binding.root){

}

class Adaptor : RecyclerView.Adapter<MainViewHolder>() {
    var dogList =  mutableListOf<DogEntity>()
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val dog = dogList[position]
        Glide.with(holder.itemView.context).load(dog.message)
            .into(holder.binding.imageview)
    }

    fun setList(list : List<DogEntity>){
        this.dogList = list.toMutableList()
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return dogList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecylerImageBinding.inflate(inflater,parent,false)
        return MainViewHolder(binding)
    }
}