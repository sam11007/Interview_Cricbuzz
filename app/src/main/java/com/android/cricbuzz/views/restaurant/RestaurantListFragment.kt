package com.android.cricbuzz.views.restaurant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.cricbuzz.databinding.ActivityMainBinding
import com.android.cricbuzz.databinding.FragmentRestaurantListBinding
import com.android.cricbuzz.network.NetworkResource

import com.android.cricbuzz.views.base.BaseFragment
import com.android.cricbuzz.views.main.MainActivityViewModel
import com.android.cricbuzz.views.restaurant.adapter.RestaurantAdapter

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantListFragment : BaseFragment<FragmentRestaurantListBinding>() {
    private val restuarantViewModel: RestaurantListViewModel by viewModels()
    @Inject
    lateinit var restaurantAdapter: RestaurantAdapter
    private val dialog by lazy {
        progressDialog("Loading....")
    }
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRestaurantListBinding {
        return FragmentRestaurantListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = restuarantViewModel
        binding?.lifecycleOwner = this
        dataObserver()
        recyclerView()
        onClick()
    }

    private fun onClick() {
        restaurantAdapter.clickListener.onItemClick = {data, position ->
            findNavController().navigate(RestaurantListFragmentDirections.actionRestaurantListToRestaurantDetails(data.id ?: 0))
        }
        binding?.searchView?.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(textSubmit: String?): Boolean {
                Log.d("===========onQueryTextSubmit", "$textSubmit")
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                Log.d("===========onQueryTextChange", "$text")
                restuarantViewModel.fetchAllRestaurantList(text)
                return false
            }

        })
    }

    private fun recyclerView() {
        binding?.rvRestaurant?.adapter = restaurantAdapter
        binding?.rvRestaurant?.setHasFixedSize(true)
    }

    private fun dataObserver() {
        restuarantViewModel.restaurantList.observe(viewLifecycleOwner){ response->
            response?.let {
                when (it) {
                    is NetworkResource.Loading -> {
                        dialog.show()
                    }
                    is NetworkResource.Success -> {
                        dialog.dismiss()
                        it.data?.let { restaurantList ->
                            restaurantAdapter.submitList(restaurantList)
                        }
                    }
                    is NetworkResource.Error -> {
                        dialog.dismiss()
                    }

                }
            }

        }
    }

}