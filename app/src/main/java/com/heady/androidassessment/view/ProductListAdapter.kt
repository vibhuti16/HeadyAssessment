package com.heady.androidassessment.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.myapplication.model.Products
import com.heady.androidassessment.R


class ProductListAdapter(private var products:List<Products>):RecyclerView.Adapter<ProductListAdapter.MViewHolder>(){

    var cat : String = ""
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_products, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        val product= products[position]
        Log.d("product.name",products.size.toString())
        vh.textViewName.text= product.name.toString()
        if(position!=0) {
            var prevCat: String = products[position-1].category
            if(prevCat.equals("")){
                prevCat = cat
            }
            if (prevCat.equals(product.category)) {
                cat = prevCat
                vh.txtHeader.visibility = View.GONE
            }else{
                vh.txtHeader.visibility = View.VISIBLE
                vh.txtHeader.text = product.category.toString()
            }
        }else{
            vh.txtHeader.visibility = View.VISIBLE
            vh.txtHeader.text = product.category.toString()

        }

        vh.linlayProducts.setTag(R.id.linlay_products,position)
        vh.linlayProducts.setOnClickListener(View.OnClickListener {
            var intent : Intent = Intent(vh.linlayProducts.context,ProductDetailActivity::class.java)
            var pos : Int = vh.linlayProducts.getTag(R.id.linlay_products) as Int
            val b = Bundle()
            b.putParcelable("product", products[pos])
            intent.putExtra("bundle",b)
            vh.linlayProducts.context.startActivity(intent)

        })
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun update(data:List<Products>){

        products= data
        notifyDataSetChanged()
    }

    class MViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val txtHeader : TextView = view.findViewById(R.id.txt_header)
        val linlayProducts : LinearLayout = view.findViewById(R.id.linlay_products)
    }
}