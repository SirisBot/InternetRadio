package com.software.osirisgadson.internetradioapp.di;


import com.software.osirisgadson.internetradioapp.data.network.radio.RadioUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    RadioUtil providesRadioUtil() {
        return new RadioUtil();
    }
}
