package com.software.osirisgadson.internetradioapp.di;


import android.content.Context;
import android.support.annotation.NonNull;

import com.software.osirisgadson.internetradioapp.data.repository.RadioRepository;
import com.software.osirisgadson.internetradioapp.data.repository.RadioRepositoryImpl;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @NonNull
    private Context appContext;

    public MainModule(@NotNull Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    RadioRepository providesRadioRepository() {
        return new RadioRepositoryImpl(appContext);
    }
}
