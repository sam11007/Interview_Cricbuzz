package com.android.cricbuzz.views.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.cricbuzz.databinding.FragmentMenuBinding
import com.android.cricbuzz.databinding.FragmentRestaurantListBinding
import com.android.cricbuzz.network.NetworkResource

import com.android.cricbuzz.views.base.BaseFragment
import com.android.cricbuzz.views.menu.menuAdapter.MenuAdapter
import com.android.cricbuzz.views.restaurant.RestaurantListFragmentDirections
import com.android.cricbuzz.views.restaurant.adapter.RestaurantAdapter

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>() {
    private val menuViewModel: MenuViewModel by viewModels()

    private val dialog by lazy {
        progressDialog("Loading....")
    }
    @Inject
    lateinit var menuAdapter: MenuAdapter

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = menuViewModel
        binding?.lifecycleOwner = this
        dataObserver()
        recyclerView()
        onClick()
    }
    private fun onClick() {
        menuAdapter.clickListener.onItemClick = {data, position ->
        }
        binding?.searchView?.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(textSubmit: String?): Boolean {
                Log.d("===========onQueryTextSubmit", "$textSubmit")


                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                Log.d("===========onQueryTextChange", "$text")
                menuViewModel.fetchAllRestaurantList(text?:"")
                return false
            }

        })
    }

    private fun recyclerView() {
        binding?.rvRestaurant?.adapter = menuAdapter
    }

    private fun dataObserver() {
        menuViewModel.restaurantList.observe(viewLifecycleOwner){ response->
            response?.let {
                when (it) {
                    is NetworkResource.Loading -> {
                        dialog.show()
                    }
                    is NetworkResource.Success -> {
                        dialog.dismiss()
                        it.data?.let { restaurantList ->
                            menuAdapter.submitList(restaurantList)
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