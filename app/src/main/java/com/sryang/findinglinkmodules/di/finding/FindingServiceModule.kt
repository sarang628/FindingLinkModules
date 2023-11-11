package com.sryang.screenfindingtest.di.finding

import android.location.Location
import com.example.screen_finding.data.RestaurantInfo
import com.example.screen_finding.usecase.FindingService
import com.example.screen_finding.usecase.SearchThisAreaUseCase
import com.example.screen_finding.viewmodel.Filter
import com.sryang.findinglinkmodules.di.finding.toFilter
import com.sryang.findinglinkmodules.di.finding.toRestaurantInfo
import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.data.SearchType
import com.sryang.torang_repository.repository.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class FindingServiceModule {
    @Provides
    fun provideFindingService(apiRestaurant: ApiRestaurant): FindingService {
        return object : FindingService {
            override suspend fun findRestaurants(): List<RestaurantInfo> {
                return apiRestaurant.getAllRestaurant(HashMap()).stream().map {
                    it.toRestaurantInfo()
                }.toList()
            }

            override suspend fun filter(filter: Filter): List<RestaurantInfo> {
                return apiRestaurant.getFilterRestaurant(
                    filter = filter.toFilter()
                )
                    .stream().map {
                        it.toRestaurantInfo()
                    }.toList()
            }
        }
    }

    @Provides
    fun provideSearchThisAreaModule(
        apiRestaurant: ApiRestaurant,
        mapRepository: MapRepository
    ): SearchThisAreaUseCase {
        return object : SearchThisAreaUseCase {
            override suspend fun invoke(filter: Filter): List<RestaurantInfo> {

                val filter = filter.toFilter()
                filter.north = mapRepository.getNElon()
                filter.east = mapRepository.getNElat()
                filter.south = mapRepository.getSWlon()
                filter.west = mapRepository.getSWlat()
                filter.searchType = SearchType.BOUND

                return apiRestaurant.getFilterRestaurant(
                    filter = filter
                )
                    .stream().map {
                        it.toRestaurantInfo()
                    }.toList()
            }
        }
    }
}

private fun newLocation(): Location {
    val location = Location("MyLocationProvider")
    return location
}
