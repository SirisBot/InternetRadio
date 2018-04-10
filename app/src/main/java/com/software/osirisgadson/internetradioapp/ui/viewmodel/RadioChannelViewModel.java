package com.software.osirisgadson.internetradioapp.ui.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.software.osirisgadson.internetradioapp.data.model.Channel;
import com.software.osirisgadson.internetradioapp.data.model.Channels;
import com.software.osirisgadson.internetradioapp.data.network.radio.RadioUtil;
import com.software.osirisgadson.internetradioapp.di.DaggerMainComponent;
import com.software.osirisgadson.internetradioapp.di.MainModule;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RadioChannelViewModel extends AndroidViewModel {

    @Inject
    RadioUtil radioUtil;

    @Nullable
    private Observable<Channels> channelsObservable;

    public RadioChannelViewModel(@NonNull Application application) {
        super(application);

        setupDaggerComponent();
    }

    private void setupDaggerComponent() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule())
                .build()
                .inject(this);
    }

    public void getRadioChannels(Observer<Channels> channelsObserver) {
        channelsObservable = radioUtil.getService().getRadioChannels();
        if (channelsObservable != null) {
            channelsObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(channelsObserver);
        }
    }

    public void getRadioChannelsByFilter(String search, Observer<Channels> channelsObserver) {
        channelsObservable = radioUtil.getService().getRadioChannels();
        if (channelsObservable != null) {
            channelsObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .debounce(2000, TimeUnit.MILLISECONDS)
                    .filter(channels -> {
                        Iterator<Channel> channelList = channels.getChannels().iterator();
                        while (channelList.hasNext()) {
                            Channel channel = channelList.next();
                            if (!StringUtils.containsIgnoreCase(channel.getDj(), search)) {
                                channelList.remove();
                            }
                        }
                        return true;
                    })
                    .subscribe(channelsObserver);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (channelsObservable != null) {
            channelsObservable.unsubscribeOn(Schedulers.io());
        }
    }
}
