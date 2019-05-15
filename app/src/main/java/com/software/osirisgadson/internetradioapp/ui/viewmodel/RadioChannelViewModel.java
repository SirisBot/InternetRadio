package com.software.osirisgadson.internetradioapp.ui.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.software.osirisgadson.internetradioapp.data.model.Channel;
import com.software.osirisgadson.internetradioapp.data.model.Channels;
import com.software.osirisgadson.internetradioapp.data.repository.RadioRepository;
import com.software.osirisgadson.internetradioapp.di.DaggerMainComponent;
import com.software.osirisgadson.internetradioapp.di.MainModule;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class RadioChannelViewModel extends AndroidViewModel {

    @Inject
    RadioRepository radioRepository;

    //use android architecture livedata in combination with viewmodel to form mvvm two way bind
    public MutableLiveData<List<Channel>> liveDataChannelList = new MutableLiveData<>();

    //change observable to backpressure-aware flowable and handle emitter buffer size
    @Nullable
    private Flowable<Channels> channelsFlowable;

    public RadioChannelViewModel(@NonNull Application application) {
        super(application);

        setupDaggerComponent();
    }

    private void setupDaggerComponent() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this.getApplication()))
                .build()
                .inject(this);
    }

    public void getRadioChannels() {
        radioRepository.getChannels(liveDataChannelList);
    }

    public void getRadioChannelsByFilter(String search) {
//        if (liveDataChannelList.getValue() != null) {
//            Flowable.just(liveDataChannelList.getValue())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .debounce(DEBOUNCE_DELAY, TimeUnit.MILLISECONDS)
//                    .filter(channels -> {
//                        Iterator<Channel> channelList = channels.iterator();
//                        while (channelList.hasNext()) {
//                            Channel channel = channelList.next();
//                            if (!StringUtils.containsIgnoreCase(channel.getDj(), search)) {
//                                channelList.remove();
//                            }
//                        }
//                        return true;
//                    })
//                    .subscribe(new Subscriber<List<Channel>>() {
//
//                        @Override
//                        public void onSubscribe(Subscription s) {
//
//                        }
//
//                        @Override
//                        public void onNext(List<Channel> channels) {
//                            liveDataChannelList.setValue(channels);
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            //log error to crashyltics or some sort of analytic
//                            Log.d(ERROR_TAG, "onError: " + e.getMessage() );
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        }
    }

    //take advantage of the viewmodel lifecycle awareness and clear unsubscribe when model is disposed
    @Override
    protected void onCleared() {
        super.onCleared();
        if (channelsFlowable != null) {
            channelsFlowable.unsubscribeOn(Schedulers.io());
        }
    }
}
