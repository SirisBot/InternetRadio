package com.software.osirisgadson.internetradioapp

import android.app.Application
import com.software.osirisgadson.internetradioapp.di.AppComponent
import com.software.osirisgadson.internetradioapp.di.AppModule
import com.software.osirisgadson.internetradioapp.di.DaggerAppComponent

class App : Application() {

    var appComponent: AppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
}