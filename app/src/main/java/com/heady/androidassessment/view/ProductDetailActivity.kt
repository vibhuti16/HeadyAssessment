package com.heady.androidassessment.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.children
import com.app.myapplication.model.Products
import com.heady.androidassessment.R
import kotlinx.android.synthetic.main.content_product_detail.*


class ProductDetailActivity : Activity() {

    lateinit var product : Products
    lateinit var colorList: ArrayList<String>
    lateinit var sizeList: ArrayList<String>
    lateinit var priceList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        if(intent.hasExtra("bundle")) {
            product = intent.getBundleExtra("bundle").getParcelable("product")

            product_name.setText(product.name)

            txt_most_viewed.setText("Most viewed : "+product.view_count)
            txt_most_ordered.setText("Most ordered : "+product.order_count)
            txt_most_shared.setText("Most shared : "+product.shares)
            colorList = ArrayList()
            sizeList = ArrayList()
            priceList = ArrayList()
            for(item in product.variants) {
                colorList.add(item.color)
                sizeList.add(item.size)
                priceList.add(item.price)
            }

            sizeList.removeAt(sizeList.size-1)

//            var count : Int=0
//            for(item in product.variants) {
//                var textColor : TextView = TextView(this)
//                textColor.setText(item.color)
//                textColor.setPadding(10,10,10,10)
//                if(count == 0){
//                    textColor.setTextColor(resources.getColor(android.R.color.black))
//                }else{
//                    textColor.setTextColor(resources.getColor(android.R.color.darker_gray))
//                }
//                    count++
//                linlay_colors.addView(textColor)
//            }
            var sizeSet: ArrayList<String> = ArrayList()
            for(item in sizeList){
                if(!sizeSet.contains(item)){
                    sizeSet.add(item)
                }
            }
           var spinnerSizeAdapter :  ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,sizeSet)
            spinnerSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            size_spinner.adapter = spinnerSizeAdapter
            size_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    var size : String = parent.getItemAtPosition(position).toString()
                    var id : Int = 0
                    var priceSel : Boolean = false
                    linlay_colors.removeAllViews()
                    for(item in product.variants) {
                        if(item.size.equals(size,ignoreCase = true)) {
                            var textColor: TextView = TextView(this@ProductDetailActivity)
                            textColor.setText(item.color)
                            textColor.setPadding(10, 10, 10, 10)
                            textColor.tag = item.size
                            textColor.setTag(R.id.glide_custom_view_target_tag,item.color)
                            textColor.setOnClickListener(View.OnClickListener {
                                var size : String = it.tag.toString()
                                var color : String = it.getTag(R.id.glide_custom_view_target_tag).toString()
                                if (it is TextView) {
                                    val textView = it

                                    for(textv in linlay_colors.children ){
                                        if(textv is TextView) {
                                            textv.setTextColor(resources.getColor(android.R.color.darker_gray))
                                        }
                                    }

                                    textView.setTextColor(resources.getColor(android.R.color.black))
                                }
                                for(item in product.variants) {
                                    if(item.size.equals(size) and item.color.equals(color)){
                                        txt_price.setText(item.price)
                                    }
                                }

                            })

                            if(!priceSel){
                                priceSel = true
                                txt_price.setText(item.price)
                                textColor.setTextColor(resources.getColor(android.R.color.black))
                            }else{
                                textColor.setTextColor(resources.getColor(android.R.color.darker_gray))
                            }

                            linlay_colors.addView(textColor)



                        }

                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
            txt_price.setTextSize(10.0f)
            txt_price.setText(priceList.get(0))

        }
    }

}
