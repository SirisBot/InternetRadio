package com.software.osirisgadson.internetradioapp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.software.osirisgadson.internetradioapp.data.model.Channel

@Database(entities = [Channel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun channelDao(): ChannelDao
}