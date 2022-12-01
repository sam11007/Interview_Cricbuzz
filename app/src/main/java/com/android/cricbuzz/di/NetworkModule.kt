package com.android.cricbuzz.di

import android.content.Context
import com.android.cricbuzz.Application
import com.android.cricbuzz.network.ToJSONObject
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides

import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    @Provides
    @Singleton
    fun provideJSON(moshi: Moshi, @ApplicationContext appContext: Context): ToJSONObject = ToJSONObject(moshi, appContext)


}