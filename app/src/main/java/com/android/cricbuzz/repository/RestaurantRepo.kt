package com.android.cricbuzz.repository


import android.util.Log
import com.android.cricbuzz.network.ToJSONObject
import com.android.cricbuzz.network.model.MenuModel
import com.android.cricbuzz.network.model.RestaurantModel
import com.android.cricbuzz.utils.Constants
import javax.inject.Inject


class RestaurantRepo @Inject constructor(
private val toJSONObject: ToJSONObject
) {
 suspend fun getAllRestaurantList(serchText: String): List<RestaurantModel.Restaurant?>? {
     try{
         val allresturant = toJSONObject.processJsonFromFile(Constants.RESTAURANT_JSON, RestaurantModel.Response::class.java)
         var allres = allresturant?.restaurants?.map {
             it?.categories = getMenuList(it?.id ?: 0)?.categories
             it
         }
         return if(serchText.isNullOrBlank()){
             allres
         }else{
             allres = allres?.filter { res -> (res?.name)?.contains(serchText, false)!!
                     || (res?.cuisineType)?.contains(serchText, false)!!
                     || ((res?.categories)?.filter {cat->
                 (cat?.menuItems)?.filter { menuItem ->  (menuItem?.name)?.contains(serchText, false)!! }?.size ?: 0 > 0
             }?.size ?: 0 > 0)
             }
             getMenuFilter(allres,serchText)
         }

     }catch (e: Exception){
         Log.d("TAG", "$=$  ${e.printStackTrace()}")
     }
     return null
 }

    private suspend fun getMenuFilter(allres: List<RestaurantModel.Restaurant?>?, serchText: String): List<RestaurantModel.Restaurant?>? {
       return allres?.map { res ->
            if(((res?.categories)?.filter {cat->
                    (cat?.menuItems)?.filter { menuItem ->  (menuItem?.name)?.contains(serchText, false)!! }?.size ?: 0 > 0
                }?.size ?: 0 > 0)){
                res?.categories = getMenuList(
                    res?.id ?: 0
                )?.categories?.map { menu ->
                    val data = menu?.menuItems?.filter { menuItem ->
                        (menuItem?.name)?.contains(serchText, false)!!
                    }
                    menu?.menuItems = data
                    menu
                }?.filter {
                    (it?.menuItems?.size ?: 0) > 0
                }
            }
            res
        }
    }

    suspend fun getRestaurantList(serchText: String?): List<RestaurantModel.Restaurant?>? {
        try{
            var allresturant = toJSONObject.processJsonFromFile(Constants.RESTAURANT_JSON, RestaurantModel.Response::class.java)
            return if(serchText.isNullOrBlank()){
                allresturant?.restaurants
            }else{
                allresturant?.restaurants?.filter { res -> (res?.name)?.contains(serchText, false)!!
                        || (res?.cuisineType)?.contains(serchText, false)!!
                        || (res?.address)?.contains(serchText, false)!!
                        || (res?.neighborhood)?.contains(serchText, false)!!
                }

            }

        }catch (e: Exception){
            Log.d("TAG", "$=$  ${e.printStackTrace()}")
        }
        return null
    }

    suspend fun getAllRestaurantDetails(restaurantId: Int): RestaurantModel.Restaurant? {
        try {
            val restaurantList = getAllRestaurantList("")
            return restaurantList?.firstOrNull { restaurantId == it?.id }
        } catch (e: Exception) {
            Log.d("TAG", "$=$  ${e.printStackTrace()}")
        }
        return null
    }

    suspend fun getAllMenuList(): MenuModel.Response? {
        try{
            val response = toJSONObject.processJsonFromFile(Constants.MENU_JSON, MenuModel.Response::class.java)
            return response
        }catch (e: Exception){
            Log.d("TAG", "$=$  ${e.printStackTrace()}")
        }
        return null
    }


    suspend fun getMenuList(resturantId: Int): MenuModel.Menu? {
        try {
            val allmenu = getAllMenuList()
            return allmenu?.menus?.firstOrNull { resturantId == it?.restaurantId }
        } catch (e: Exception) {
            Log.d("TAG", "$=$  ${e.printStackTrace()}")
        }
        return null
    }

}