package com.heady.androidassessment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.myapplication.model.Categories
import com.heady.androidassessment.R


class CategoryListAdapter(private var museums:List<Categories>):RecyclerView.Adapter<CategoryListAdapter.MViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_rankings, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        val museum= museums[position]

        //render
        vh.textViewName.text= museum.name
//        Glide.with(vh.imageView.context).load(museum.photo).into(vh.imageView)
    }

    override fun getItemCount(): Int {
        return museums.size
    }

    fun update(data:List<Categories>){
        museums= data
        notifyDataSetChanged()
    }

    class MViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textViewName: TextView = view.findViewById(R.id.textViewName)
    }
}