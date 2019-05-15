package com.software.osirisgadson.internetradioapp.data.network.radio;



import com.software.osirisgadson.internetradioapp.data.model.Channels;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RadioService {

    @GET("/jvanaria/jvanaria.github.io/master/channels.json")
    Flowable<Channels> getRadioChannels();
}
