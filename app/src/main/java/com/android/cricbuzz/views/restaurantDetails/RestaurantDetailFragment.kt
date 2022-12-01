package com.android.cricbuzz.views.restaurantDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.cricbuzz.databinding.FragmentRestaurantListBinding
import com.android.cricbuzz.databinding.FragmentResturantDetailsBinding
import com.android.cricbuzz.network.NetworkResource

import com.android.cricbuzz.views.base.BaseFragment
import com.android.cricbuzz.views.restaurant.adapter.RestaurantAdapter
import com.android.cricbuzz.views.restaurantDetails.dayslist.DaysAdapter
import com.android.cricbuzz.views.restaurantDetails.dayslist.DaysModel
import com.android.cricbuzz.views.restaurantDetails.resturantMenu.RestaurantMenuAdapter

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantDetailFragment : BaseFragment<FragmentResturantDetailsBinding>() {
    private val detailVieModel: RestaurantDetailViewModel by viewModels()
    private val navArgs: RestaurantDetailFragmentArgs by navArgs()

    private val dialog by lazy {
        progressDialog("Loading....")
    }
    private val daysList = ArrayList<DaysModel>()
    @Inject
    lateinit var daysAdapter: DaysAdapter

    @Inject
    lateinit var restaurantMenuAdapter: RestaurantMenuAdapter

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentResturantDetailsBinding {
        return FragmentResturantDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = detailVieModel
        binding?.lifecycleOwner = this
        dataObserver()
        binding?.timing?.adapter = daysAdapter
        binding?.rvMenu?.adapter = restaurantMenuAdapter
        detailVieModel.fetchAllRestaurantDetails(navArgs.restuarantId)
    }


    private fun dataObserver() {
        detailVieModel.restaurantDetails.observe(viewLifecycleOwner){ response->
            response?.let {
                when (it) {
                    is NetworkResource.Loading -> {
                        dialog.show()
                    }
                    is NetworkResource.Success -> {
                        dialog.dismiss()
                        it.data?.operatingHours?.let { days ->
                            days.monday?.let {
                                daysList.add(DaysModel(1,"Monday", days.monday))
                            }
                            days.tuesday?.let {
                                daysList.add(DaysModel(1,"Tuesday", days.tuesday))
                            }
                            days.wednesday?.let {
                                daysList.add(DaysModel(1,"Wednesday", days.wednesday))
                            }
                            days.thursday?.let {
                                daysList.add(DaysModel(1,"Thrusday", days.thursday))
                            }
                            days.friday?.let {
                                daysList.add(DaysModel(1,"Friday", days.friday))
                            }
                            days.saturday?.let {
                                daysList.add(DaysModel(1,"Saturday", days.saturday))
                            }
                            days.sunday?.let {
                                daysList.add(DaysModel(1,"Sunday", days.sunday))
                            }
                        }
                        daysAdapter.submitList(daysList)
                        restaurantMenuAdapter.submitList(it.data?.categories)
                    }
                    is NetworkResource.Error -> {
                        dialog.dismiss()
                    }

                }
            }

        }
    }

}