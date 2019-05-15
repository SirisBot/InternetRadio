package com.software.osirisgadson.internetradioapp.di

import com.software.osirisgadson.internetradioapp.data.repository.RadioRepositoryImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(radioRepository: RadioRepositoryImpl)
}