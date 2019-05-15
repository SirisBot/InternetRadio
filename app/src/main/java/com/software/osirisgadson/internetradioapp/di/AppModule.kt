package com.software.osirisgadson.internetradioapp.di

import android.arch.persistence.room.Room
import android.content.Context
import com.software.osirisgadson.internetradioapp.data.db.AppDatabase
import com.software.osirisgadson.internetradioapp.data.db.ChannelDao
import com.software.osirisgadson.internetradioapp.data.network.radio.RadioUtil
import dagger.Module
import dagger.Provides

@Module
class AppModule(appContext: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "radio-database"
    ).build()

    @Provides
    fun providesChannelDao(): ChannelDao {
        return db.channelDao();
    }

    @Provides
    fun providesRadioUtil(): RadioUtil {
        return RadioUtil()
    }
}