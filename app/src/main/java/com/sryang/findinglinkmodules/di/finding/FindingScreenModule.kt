package com.sryang.findinglinkmodules.di.finding

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cardinfo.RestaurantCardPage
import com.example.cardinfo.RestaurantCardViewModel
import com.example.screen_finding.ui.FindScreen
import com.example.screen_finding.viewmodel.FindingViewModel
import com.example.screen_map.compose.CurrentLocationScreen
import com.example.screen_map.compose.MapScreen
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.sryang.screen_filter.ui.FilterScreen
import com.sryang.screen_filter.ui.FilterViewModel
import com.sryang.screenfindingtest.di.finding.toFilter
import com.sryang.screenfindingtest.di.finding.toMarkData
import com.sryang.screenfindingtest.di.finding.toRestaurantCardData
import kotlinx.coroutines.launch

@Composable
fun Finding(
    findingViewModel: FindingViewModel = hiltViewModel(),
    restaurantCardViewModel: RestaurantCardViewModel = hiltViewModel(),
    filterViewModel: FilterViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by findingViewModel.uiState.collectAsState()
    val filterUiState by filterViewModel.uiState.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(true) }
    var myLocation: LatLng? by remember { mutableStateOf(null) }

    Log.d("_FindingScreenModule", filterUiState.distance)


    FindScreen(
        restaurantCardPage = {
            RestaurantCardPage(
                restaurants = uiState.restaurants.stream().map { it.toRestaurantCardData() }
                    .toList(),
                restaurantImageUrl = "http://sarang628.iptime.org:89/restaurant_images/",
                onChangePage = { page ->
                    findingViewModel.selectPage(page)
                },
                onClickCard = { navController.navigate("restaurant/$it") },
                selectedRestaurant = uiState.selectedRestaurant?.toRestaurantCardData(),
                visible = isVisible
            )
        },
        mapScreen = {
            Box {
                MapScreen(
                    onMark = {
                        isVisible = true
                        findingViewModel.selectMarker(it)
                    },
                    cameraPositionState = cameraPositionState,
                    list = uiState.restaurants.stream().map { it.toMarkData() }.toList(),
                    selectedMarkerData = uiState.selectedRestaurant?.toMarkData(),
                    onMapClick = {
                        isVisible = !isVisible
                        Log.d("Finding", "onMapClick $isVisible")
                    },
                    myLocation = myLocation,
                    boundary = filterUiState.distance.toBoundary()
                )
            }
        },
        onZoomIn = {
            coroutineScope.launch {
                cameraPositionState.animate(CameraUpdateFactory.zoomIn(), 300)
            }
        },
        onZoomOut = {
            coroutineScope.launch {
                cameraPositionState.animate(CameraUpdateFactory.zoomOut(), 300)
            }
        },
        filter = {
            FilterScreen(filterViewModel = filterViewModel, onFilter = {
                val filter = it.toFilter()
                filter.lat = myLocation?.latitude
                filter.lon = myLocation?.longitude
                findingViewModel.filter(filter)
            }, visible = isVisible)
        },
        myLocation = {
            CurrentLocationScreen(onLocation = {
                findingViewModel.setCurrentLocation(it)
                coroutineScope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLng(
                            LatLng(
                                it.latitude,
                                it.longitude
                            )
                        ),
                        300
                    )
                }
                myLocation = LatLng(it.latitude, it.longitude)
            }
            )
        }
    )
}

fun String.toBoundary(): Double {
    if (this.equals("100m")) {
        return 100.0
    } else if (this.equals("300m")) {
        return 300.0
    } else if (this.equals("500m")) {
        return 500.0
    } else if (this.equals("1km")) {
        return 1000.0
    } else if (this.equals("3km")) {
        return 3000.0
    }
    return 0.0
}