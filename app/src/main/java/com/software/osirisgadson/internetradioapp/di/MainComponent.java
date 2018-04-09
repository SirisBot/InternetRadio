package com.software.osirisgadson.internetradioapp.di;


import com.software.osirisgadson.internetradioapp.ui.viewmodel.RadioChannelViewModel;

import dagger.Component;

@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(RadioChannelViewModel radioChannelViewModel);
}
