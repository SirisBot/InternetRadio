package com.software.osirisgadson.internetradioapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Channels(@SerializedName("channels")
                    @Expose
                    var channels: List<Channel>?) {
}