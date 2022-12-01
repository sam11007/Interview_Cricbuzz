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
    /**
     * Fetch All Restaurant and food items according to the search ket
     * @param searchableString String
     * @return List<RestaurantModel.Restaurant?>?
     */
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
             allres = allres?.filter { res -> (res?.name)?.contains(serchText, true)!!
                     || (res?.cuisineType)?.contains(serchText, true)!!
                     || ((res?.categories)?.filter {cat->
                 (cat?.menuItems)?.filter { menuItem ->  (menuItem?.name)?.contains(serchText, true)!! }?.size ?: 0 > 0
             }?.size ?: 0 > 0)
             }
             getMenuFilter(allres,serchText)
         }

     }catch (e: Exception){
         Log.d("TAG", "$=$  ${e.printStackTrace()}")
     }
     return null
 }
    /**
     * Food Menu filter
     * @param allres List<RestaurantModel.Restaurant?>?
     * @param searchableString String
     * @return List<RestaurantModel.Restaurant?>?
     */
    private suspend fun getMenuFilter(allres: List<RestaurantModel.Restaurant?>?, serchText: String): List<RestaurantModel.Restaurant?>? {
       return allres?.map { res ->
            if(((res?.categories)?.filter {cat->
                    (cat?.menuItems)?.filter { menuItem ->  (menuItem?.name)?.contains(serchText, true)!! }?.size ?: 0 > 0
                }?.size ?: 0 > 0)){
                res?.categories = getMenuList(
                    res?.id ?: 0
                )?.categories?.map { menu ->
                    val data = menu?.menuItems?.filter { menuItem ->
                        (menuItem?.name)?.contains(serchText, true)!!
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
    /**
     * get only restaurant list with search text
     * @param searchableString String
     * @return List<RestaurantModel.Restaurant?>?
     */
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
    /**
     * get only restaurant Details accoring to resturant Id
     * @param restaurantId Int
     * @return RestaurantModel.Restaurant?
     */
    suspend fun getAllRestaurantDetails(restaurantId: Int): RestaurantModel.Restaurant? {
        try {
            val restaurantList = getAllRestaurantList("")
            return restaurantList?.firstOrNull { restaurantId == it?.id }
        } catch (e: Exception) {
            Log.d("TAG", "$=$  ${e.printStackTrace()}")
        }
        return null
    }
    /**
     * get only Menu List
     * @return MenuModel.Response?
     */
    suspend fun getAllMenuList(): MenuModel.Response? {
        try{
            val response = toJSONObject.processJsonFromFile(Constants.MENU_JSON, MenuModel.Response::class.java)
            return response
        }catch (e: Exception){
            Log.d("TAG", "$=$  ${e.printStackTrace()}")
        }
        return null
    }

    /**
     * get only Menu Item List
     * @param resturantId String
     * @return MenuModel.Menu?
     */
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