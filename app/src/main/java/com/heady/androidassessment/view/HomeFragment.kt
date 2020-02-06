package com.heady.androidassessment.view

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.myapplication.model.*
import com.heady.androidassessment.R
import com.heady.androidassessment.dao.Product
import com.heady.androidassessment.di.Injection
import com.heady.androidassessment.publicdel.ViewModelFactory
import com.heady.androidassessment.viewmodel.EcommerceViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_error.*


class HomeFragment : Fragment() {

    private lateinit var viewModel: EcommerceViewModel
    private lateinit var adapter: CategoryListAdapter
    private lateinit var productsAdapter: ProductListAdapter
    private lateinit var rankingsAdapter: RankingsAdapter
    var products: ArrayList<Products> = ArrayList()
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupUI()

        viewModel.loadEcommerceData()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_home, null)
        drawerLayout = view.findViewById(R.id.drawer_layout)

        val btnSortByRating: Button = view.findViewById(R.id.btn_sort)
        btnSortByRating.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                drawerLayout.openDrawer(GravityCompat.END)
            }

        })

        return view
    }

    //ui
    private fun setupUI() {

        adapter = CategoryListAdapter(viewModel.ecommerceD.value?.categories
                ?: ArrayList<Categories>())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        productsAdapter = ProductListAdapter(viewModel.productsD.value ?: ArrayList<Products>())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = productsAdapter

        //onclick for ratings sorting filter
        val listener = object : RankingsAdapter.OnRowClickedListener {
            override fun onRowClicked(position: Int) {

                if (position == 1) {

                    val myObjectList: MutableList<Products> = mutableListOf()

                    for(item in products){
                        myObjectList.add(item)
                    }
                    var sortedByName =  myObjectList.sortedBy { myObject -> myObject.view_count }
                    productsAdapter.update(sortedByName)

                } else if (position == 2) {
                    val myObjectList: MutableList<Products> = mutableListOf()

                    for(item in products){
                        myObjectList.add(item)
                    }
                    var sortedByName =  myObjectList.sortedByDescending { myObject -> myObject.view_count }
                    productsAdapter.update(sortedByName)

                } else if (position == 4) {

                    val myObjectList: MutableList<Products> = mutableListOf()

                    for(item in products){
                        myObjectList.add(item)
                    }
                    var sortedByName =  myObjectList.sortedBy { myObject -> myObject.order_count }
                    productsAdapter.update(sortedByName)

                } else if (position == 5) {
                    val myObjectList: MutableList<Products> = mutableListOf()

                    for(item in products){
                        myObjectList.add(item)
                    }
                    var sortedByName =  myObjectList.sortedByDescending { myObject -> myObject.order_count }
                    productsAdapter.update(sortedByName)

                } else if (position == 7) {

                    val myObjectList: MutableList<Products> = mutableListOf()

                    for(item in products){
                        myObjectList.add(item)
                    }
                    var sortedByName =  myObjectList.sortedBy { myObject -> myObject.shares }
                    productsAdapter.update(sortedByName)

                } else if (position == 8) {
                    val myObjectList: MutableList<Products> = mutableListOf()

                    for(item in products){
                        myObjectList.add(item)
                    }
                    var sortedByName =  myObjectList.sortedByDescending { myObject -> myObject.shares }
                    productsAdapter.update(sortedByName)
                }

                drawerLayout.closeDrawer(GravityCompat.END)
            }

        }
        rankingsAdapter = RankingsAdapter(viewModel.rankingsList.value
                ?: ArrayList<String>(), listener)
        rv_rankings.layoutManager = LinearLayoutManager(activity)
        rv_rankings.adapter = rankingsAdapter

        var rankings: ArrayList<String> = ArrayList()
        rankings.add("Most Viewed Products")
        rankings.add("Sort Ascending")
        rankings.add("Sort Descending")
        rankings.add("Most Ordered Products")
        rankings.add("Sort Ascending")
        rankings.add("Sort Descending")
        rankings.add("Most Shared Products")
        rankings.add("Sort Ascending")
        rankings.add("Sort Descending")

        rankingsAdapter.update(rankings)
    }



    private fun setupViewModel() {
        context?.let {
            viewModel = ViewModelProviders.of(this, ViewModelFactory(activity!!.applicationContext, Injection.providerRepository())).get(EcommerceViewModel::class.java)
            viewModel.ecommerceD.observe(this, renderMuseums)

            viewModel.isViewLoading.observe(this, isViewLoadingObserver)
            viewModel.onMessageError.observe(this, onMessageErrorObserver)
            viewModel.isEmptyList.observe(this, emptyListObserver)


            viewModel.allProducts.observe(this, Observer {

                products = ArrayList()
                var category: String = ""

                Log.d("products retrieved", it.size.toString());
                for (item in it) {

                    var tax: Tax = Tax(item.tax_name, item.tax_value)

                    //conver
                    var variants_ids: List<String> = item.variants_id.split("|")
                    var variants_color: List<String> = item.variants_color.split("|")
                    var variants_size: List<String> = item.variants_size.split("|")
                    var variants_price: List<String> = item.variants_price.split("|")

                    var variantsList: ArrayList<Variants> = ArrayList()
                    for (i in 0..(variants_ids.size - 1)) {
                        var variants: Variants = Variants(variants_ids.get(i), variants_color.get(i), variants_size.get(i), variants_price.get(i))
                        variantsList.add(variants)
                    }


                    var product: Products = Products(item.id, item.name, item.date_added, item.most_viewed_ranking,item.most_ordered_ranking,item.most_shared_ranking, tax, item.category_name, variantsList)
                    products.add(product)


                }
                Log.d("products observe", products.size.toString())
                productsAdapter.update(products)




            })



            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {

//                if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(activity, "No Match found",Toast.LENGTH_LONG).show();
//                }

//                    if(query!=null && !query.isEmpty()) {
//                        var productsFiltered: ArrayList<Products> = ArrayList()
//                        for (item in products) {
//                            if (item.name.contains(query.toString(), ignoreCase = true)) {
//
//                                productsFiltered.add(item)
//                            }
//                        }
////
//                        productsAdapter.update(productsFiltered)
//                    }else{
//                        productsAdapter.update(products)
//                    }
                    return false;
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (query != null && !query.isEmpty()) {
                        var productsFiltered: ArrayList<Products> = ArrayList()
                        for (item in products) {
                            if (item.name.startsWith(query.toString(), ignoreCase = true)) {

                                productsFiltered.add(item)
                            }
                        }
//
                        productsAdapter.update(productsFiltered)
                    } else {
                        productsAdapter.update(products)
                    }
                    return false;
                }

            })

            searchView.setOnCloseListener(object : SearchView.OnCloseListener {
                override fun onClose(): Boolean {
                    productsAdapter.update(products)
                    return false;
                }
            })


        }

    }

    //observers
    private val renderMuseums = Observer<EcommerceData> {
        Log.v(HomeActivity.TAG, "data updated $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        if (it != null && it.categories != null) {
//            adapter.update(it.categories)
        }
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(HomeActivity.TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(HomeActivity.TAG, "onMessageError $it")
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(HomeActivity.TAG, "emptyListObserver $it")
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }


    //If you require updated data, you can call the method "loadMuseum" here
    override fun onResume() {
        Log.d("onresume ", "onresume ");
        super.onResume()

    }


}