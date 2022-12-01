package com.android.cricbuzz.views.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cricbuzz.network.NetworkResource
import com.android.cricbuzz.network.model.RestaurantModel

import com.android.cricbuzz.repository.RestaurantRepo
import com.bumptech.glide.Glide.init
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val restaurantRepo: RestaurantRepo
) : ViewModel() {
    init {
        fetchAllRestaurantList("")
    }
    private var _restaurantList = MutableLiveData<NetworkResource<List<RestaurantModel.Restaurant?>>>()
    val restaurantList: LiveData<NetworkResource<List<RestaurantModel.Restaurant?>>> = _restaurantList

    /**
     * Fech All Restaurant List and search Restaurant According to the searchKey
     * @param SearchText String
     * @return null
     */
     fun fetchAllRestaurantList(searchText: String?){
        viewModelScope.launch(Dispatchers.IO){
            val response = restaurantRepo.getRestaurantList(searchText)
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