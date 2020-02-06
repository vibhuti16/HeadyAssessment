package com.heady.androidassessment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.heady.androidassessment.R


class RankingsAdapter(private var rankings:ArrayList<String>,val listener: OnRowClickedListener):RecyclerView.Adapter<RankingsAdapter.MViewHolder>(){

    var cat : String = ""
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_rankings, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        val ranking= rankings.get(position)

        vh.textViewName.text= ranking


        if(position%3==0){
            vh.constraintTop.setBackgroundColor(vh.constraintTop.context.resources.getColor(android.R.color.darker_gray))
        }else{
            vh.constraintTop.setBackgroundColor(vh.constraintTop.context.resources.getColor(android.R.color.white))
            vh.constraintTop.setTag(R.id.constraint_top,position)
            vh.constraintTop.setOnClickListener(View.OnClickListener {
                listener.onRowClicked(position)
            })

        }

    }

    override fun getItemCount(): Int {
        return rankings.size
    }

    fun update(data:ArrayList<String>){

        rankings= data
        notifyDataSetChanged()
    }

    class MViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val constraintTop : ConstraintLayout = view.findViewById(R.id.constraint_top)

    }

    interface OnRowClickedListener{
        public fun onRowClicked(position: Int)
    }




}