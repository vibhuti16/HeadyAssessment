package com.heady.androidassessment.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.myapplication.model.Categories
import com.heady.androidassessment.data.EcommerceResponse
import com.heady.androidassessment.data.OperationCallback
import com.heady.androidassessment.model.EcommerceDataSource
import com.app.myapplication.model.EcommerceData
import com.app.myapplication.model.Products
import com.app.myapplication.model.Rankings
import com.heady.androidassessment.dao.Category
import com.heady.androidassessment.dao.EcommerceDatabase
import com.heady.androidassessment.dao.EcommerceRepository
import com.heady.androidassessment.dao.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class EcommerceViewModel(private val repository: EcommerceDataSource,application: Context):ViewModel() {

    private val  ecommerceData= MutableLiveData<EcommerceData>().apply { value = null }
    val ecommerceD: LiveData<EcommerceData> = ecommerceData

    private val  products= MutableLiveData<List<Products>>().apply { value = null }
    val productsD: LiveData<List<Products>> = products

    private val  rankings= MutableLiveData<ArrayList<String>>().apply { value = null }
    val rankingsList: LiveData<ArrayList<String>> = rankings

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList

    // The ViewModel maintains a reference to the repository to get data.
    private val ecommerceRepository: EcommerceRepository
    // LiveData gives us updated words when they change.
    val allCategory: LiveData<List<Category>>
    var allProducts: LiveData<List<Product>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val ecommerceDao = EcommerceDatabase.getDatabase(application,viewModelScope).getDao()
        ecommerceRepository = EcommerceRepository(ecommerceDao)
        allCategory = ecommerceRepository.allCategory
        allProducts = ecommerceRepository.allProducts

    }

    fun loadEcommerceData(){
        Log.d("fetching data ", "fetching data ");
        _isViewLoading.postValue(true)
        repository.retrieveJson(object:OperationCallback{
            override fun onError(obj: Any?) {
                Log.d("error data ", "error data ");
                _isViewLoading.postValue(false)
                _onMessageError.postValue( obj)
            }

            override fun onSuccess(obj: Any?) {
                Log.d("success data ", "success data ");
                _isViewLoading.postValue(false)

                if(obj!=null){
                    Log.d("success data obj ", obj.toString());
                    ecommerceData.value= obj as EcommerceData?

                         var categoriesList :List<Categories> = obj.categories
                    var productsList :ArrayList<Products> = ArrayList<Products>()

                        for (item in categoriesList) {

                            var category : Category = Category(item.id,item.name)
                            insertCategories(category)
                            for (prod in item.products) {
                                var product : Product = Product(prod.id,prod.name,prod.date_added,prod.tax.name,prod.tax.value,item.id,item.name)
                                for (variants in prod.variants) {
                                    product.variants_id = product.variants_id + variants.id + "|"
                                    product.variants_color = product.variants_color + variants.color+ "|"
                                    product.variants_price = product.variants_price + variants.price+ "|"
                                    product.variants_size = product.variants_size + variants.size+ "|"

                                }

                                productsList.add(prod)
                                insertProducts(product)
                            }



                            products.value = productsList

                        }

                    var rankingsList :List<Rankings> = obj.rankings
                    for (item in rankingsList) {
                        var ranking : String = item.ranking
                        var productsList :ArrayList<Products> =ArrayList<Products>()
                        productsList = item.products.toCollection(productsList)
                        for (prod in productsList) {
                            if(ranking.equals("Most Viewed Products",ignoreCase = true)){
                                updateProductsMostViewed(prod.view_count,prod.id)
                            }else if(ranking.equals("Most OrdeRed Products",ignoreCase = true)){
                                updateProductsMostOrdered(prod.order_count,prod.id)
                            }else if(ranking.equals("Most ShaRed Products",ignoreCase = true)){
                                updateProductsMostShared(prod.shares,prod.id)
                            }

                        }
                    }

                    rankings.value = ArrayList()
                    rankings.value?.add("Most Viewed Products")
                    rankings.value?.add("Sort Ascending")
                    rankings.value?.add("Sort Descending")
                    rankings.value?.add("Most OrdeRed Products")
                    rankings.value?.add("Sort Ascending")
                    rankings.value?.add("Sort Descending")
                    rankings.value?.add("Most ShaRed Products")
                    rankings.value?.add("Sort Ascending")
                    rankings.value?.add("Sort Descending")

                }
            }
        })
    }

    fun insertCategories(categories: Category) = viewModelScope.launch {
        ecommerceRepository.insert(categories)
    }



    fun insertProducts(product: Product) = viewModelScope.launch {


        ecommerceRepository.insert(product)
    }

    fun updateProductsMostViewed(most_viewed_ranking : Int,productId : Int) = viewModelScope.launch {


        ecommerceRepository.updateMostViewed(most_viewed_ranking,productId)
    }

    fun updateProductsMostOrdered(most_ordered_ranking : Int,productId : Int) = viewModelScope.launch {


        ecommerceRepository.updateMostOrdered(most_ordered_ranking,productId)
    }

    fun updateProductsMostShared(most_shared_ranking : Int,productId : Int) = viewModelScope.launch {


        ecommerceRepository.updateMostShared(most_shared_ranking,productId)
    }



}