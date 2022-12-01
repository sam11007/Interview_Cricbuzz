package com.android.cricbuzz.views.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cricbuzz.network.NetworkResource
import com.android.cricbuzz.network.model.MenuModel
import com.android.cricbuzz.network.model.RestaurantModel

import com.android.cricbuzz.repository.RestaurantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val restaurantRepo: RestaurantRepo
) : ViewModel() {
    init {
        fetchAllRestaurantList()
    }
    private var _restaurantList = MutableLiveData<NetworkResource<List<RestaurantModel.Restaurant?>>>()
    val restaurantList: LiveData<NetworkResource<List<RestaurantModel.Restaurant?>>> = _restaurantList

     fun fetchAllRestaurantList(searchableString: String = ""){
        viewModelScope.launch(Dispatchers.IO){

            val response = restaurantRepo.getAllRestaurantList(searchableString)
            val restaurantListRes = if(response != null) {
                NetworkResource.Success(response)
            }else{
                NetworkResource.Error("No data found")
            }
            withContext(Dispatchers.Main) {
                _restaurantList.postValue(restaurantListRes)
            }
        }
    }
}