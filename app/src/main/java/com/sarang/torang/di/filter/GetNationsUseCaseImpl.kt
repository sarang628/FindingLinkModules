package com.sarang.torang.di.filter

import com.sarang.torang.api.ApiFilter
import com.sarang.torang.data.remote.response.NationApiModel
import com.sarang.torang.BuildConfig
import com.sryang.screen_filter.data.Nation
import com.sryang.screen_filter.usecase.GetNationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetNationsUseCaseImpl {
    @Provides
    fun providesGetNationsUseCase(apiFilter: ApiFilter): GetNationsUseCase {
        return object : GetNationsUseCase {
            override suspend fun invoke(): List<Nation> {
                return apiFilter.getNations().map { it.toCity() }
            }
        }
    }
}

fun NationApiModel.toCity(): Nation {
    return Nation(
        id = this.id,
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
        url = "${BuildConfig.SERVER_URL}:${BuildConfig.IMAGE_PORT}/${this.url}",
        zoom = this.zoom
    )
}