package com.software.osirisgadson.internetradioapp.ui.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.software.osirisgadson.internetradioapp.data.model.Channel;
import com.software.osirisgadson.internetradioapp.data.model.Channels;
import com.software.osirisgadson.internetradioapp.data.network.radio.RadioUtil;
import com.software.osirisgadson.internetradioapp.di.DaggerMainComponent;
import com.software.osirisgadson.internetradioapp.di.MainModule;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RadioChannelViewModel extends AndroidViewModel {

    private static final int DEBOUNCE_DELAY = 2000;

    private static final String ERROR_TAG = "error";

    @Inject
    RadioUtil radioUtil;

    //use android architecture livedata in combination with viewmodel to form mvvm two way bind
    public MutableLiveData<List<Channel>> liveDataChannelList = new MutableLiveData<>();

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

    public void getRadioChannels() {
        channelsObservable = radioUtil.getService().getRadioChannels();
        if (channelsObservable != null) {
            channelsObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Channels>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Channels channels) {
                            liveDataChannelList.setValue(channels.getChannels());
                        }

                        @Override
                        public void onError(Throwable e) {
                            //log error to crashyltics or some sort of analytic
                            Log.d(ERROR_TAG, "onError: " + e.getMessage() );
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void getRadioChannelsByFilter(String search) {
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
                    .subscribe(new Observer<Channels>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Channels channels) {
                            liveDataChannelList.setValue(channels.getChannels());
                        }

                        @Override
                        public void onError(Throwable e) {
                            //log error to crashyltics or some sort of analytic
                            Log.d(ERROR_TAG, "onError: " + e.getMessage() );
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
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
