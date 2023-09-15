package com.dms.sephoratest.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // @Provides
    // @Singleton
    // fun provideSnakeDatabase(app: Application): SnakeDatabase {
    //     return Room.databaseBuilder(
    //         app,
    //         SnakeDatabase::class.java,
    //         SnakeDatabase.DATABASE_NAME
    //     ).build()
    // }

}