package com.software.osirisgadson.internetradioapp.data.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.software.osirisgadson.internetradioapp.App
import com.software.osirisgadson.internetradioapp.data.db.ChannelDao
import com.software.osirisgadson.internetradioapp.data.model.Channel
import com.software.osirisgadson.internetradioapp.data.model.Channels
import com.software.osirisgadson.internetradioapp.data.network.radio.RadioUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RadioRepositoryImpl(appContext: Context) : RadioRepository {

    var channelCache: List<Channel>? = null

    private val DEBOUNCE_DELAY = 2000L

    private val ERROR_TAG = "error"

    @Inject
    lateinit var channelDao: ChannelDao

    @Inject
    lateinit var radioUtil: RadioUtil

    init {
        (appContext as App).appComponent.inject(this)
    }

    override fun getChannels(liveData: MutableLiveData<List<Channel>>) {
        Observable.concatArrayEager(getChannelCache(), getRemoteChannels())
                .subscribeOn(AndroidSchedulers.mainThread())
                .materialize()
                .filter { !it.isOnError }
                .dematerialize<List<Channel>>()
                .debounce(DEBOUNCE_DELAY, TimeUnit.MILLISECONDS)
                .subscribe({
                    liveData.value = it
                })
    }

    private fun getChannelCache(): Observable<List<Channel>> {
        return channelDao.getAll()
                .filter { true }
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    channelCache = it
                }
    }

    private fun getRemoteChannels(): Observable<Channels> {
        return radioUtil.getService().radioChannels.toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    storeRemoteChannels(it.channels)
                }
    }

    private fun storeRemoteChannels(channels: List<Channel>?) {
        Observable.just(channelDao.insertAll(channels ?: emptyList()))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext {
                    println("Successfully stored users")
                }
    }
}