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

    private static final int DEBOUNCE_DELAY = 2000;

    @Inject
    RadioUtil radioUtil;

    //Ideally I would look to a LiveData implementation here to take advantage
    //of the livecycle awareness and the two-way bind mechanism but
    //opted to use the built on rxjava2 observable for familiarity sake

    //opted to use observable here due to list size requiring a relatively small buffer here
    //may look to flowable if list of items increases dramatically and increase the buffer size to handle backpressure
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
                    .debounce(DEBOUNCE_DELAY, TimeUnit.MILLISECONDS)
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

    //take advantage of the viewmodel lifecycle awareness and clear unsubscribe when model is disposed
    @Override
    protected void onCleared() {
        super.onCleared();
        if (channelsObservable != null) {
            channelsObservable.unsubscribeOn(Schedulers.io());
        }
    }
}
