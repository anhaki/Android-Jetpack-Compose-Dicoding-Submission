package com.haki.hqrecipe.di

import com.haki.hqrecipe.data.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}