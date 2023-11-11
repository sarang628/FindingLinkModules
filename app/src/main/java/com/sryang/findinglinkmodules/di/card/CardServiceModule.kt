package com.example.cardinfoscreentestapp.di.card

import com.example.cardinfo.RestaurantCardData
import com.example.cardinfo.RestaurantCardService
import com.sryang.torang_repository.api.ApiRestaurant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class CardServiceModule {
    @Provides
    fun provideCardService(restaurant: ApiRestaurant): RestaurantCardService {
        return object : RestaurantCardService {
            override suspend fun getCardList(): List<RestaurantCardData> {
                return restaurant.getAllRestaurant(HashMap()).stream().map {
                    RestaurantCardData(
                        restaurantId = it.restaurantId,
                        restaurantName = it.restaurantName,
                        rating = it.rating,
                        foodType = it.restaurantType,
                        restaurantImage = it.imgUrl1,
                        price = "$$",
                        distance = "150M"
                    )
                }.toList()
            }
        }
    }
}