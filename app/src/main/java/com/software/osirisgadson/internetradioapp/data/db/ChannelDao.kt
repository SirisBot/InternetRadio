package com.software.osirisgadson.internetradioapp.data.db

import android.arch.persistence.room.*
import com.software.osirisgadson.internetradioapp.data.model.Channel
import io.reactivex.Single

@Dao
interface ChannelDao {

    @Query("Select * FROM channel")
    fun getAll(): Single<List<Channel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg channel: Channel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(channels: List<Channel>)

    @Delete
    fun delete(channel: Channel)

}