package com.android.cricbuzz.views.restaurantDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cricbuzz.network.NetworkResource
import com.android.cricbuzz.network.model.RestaurantModel

import com.android.cricbuzz.repository.RestaurantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    private val restaurantRepo: RestaurantRepo
) : ViewModel() {

private var _restaurantDetails = MutableLiveData<NetworkResource<RestaurantModel.Restaurant>>()
    val restaurantDetails: LiveData<NetworkResource<RestaurantModel.Restaurant>> = _restaurantDetails

    /**
     * Fech All Restaurant Details
     * @param restaurantId String
     * @return null
     */
     fun fetchAllRestaurantDetails(restaurantId: Int){
        viewModelScope.launch(Dispatchers.IO){
            val response = restaurantRepo.getAllRestaurantDetails(restaurantId)
            val restaurantRes = if(response != null) {
                NetworkResource.Success(response)
            }else{
                NetworkResource.Error("No data found")
            }
            withContext(Dispatchers.Main) {
                _restaurantDetails.postValue(restaurantRes)
            }
        }
    }

}