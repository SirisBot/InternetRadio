package com.software.osirisgadson.internetradioapp.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.software.osirisgadson.internetradioapp.data.model.Channel

interface RadioRepository {

    fun getChannels(liveData: MutableLiveData<List<Channel>>)
}