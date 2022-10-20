package io.github.tuguzt.flexibleproject.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.flexibleproject.data.datasource.local.ObjectBox
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun providesObjectBox(@ApplicationContext context: Context): ObjectBox = ObjectBox(context)
}
