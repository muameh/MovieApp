package com.mehmetBaloglu.mymovieapp_v1.di


import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import com.mehmetBaloglu.mymovieapp_v1.data.repository.MovieRepo
import com.mehmetBaloglu.mymovieapp_v1.retrofit.ApiDao
import com.mehmetBaloglu.mymovieapp_v1.retrofit.ApiUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiDao () : ApiDao {
        return ApiUtils.getDao()
    }

    @Provides
    @Singleton
    fun provideRepository(apiDao: ApiDao): MovieRepo {
        return MovieRepo(apiDao)
    }





}

