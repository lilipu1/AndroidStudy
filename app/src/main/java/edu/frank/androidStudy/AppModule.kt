package edu.frank.androidStudy

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideApiService():ApiService{
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .build()
            .create(ApiService::class.java)
    }
}