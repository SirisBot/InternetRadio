package com.software.osirisgadson.internetradioapp.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Channel(@PrimaryKey
                   @SerializedName("id")
                   @Expose
                   var id: String,

                   @SerializedName("title")
                   @Expose
                   var title: String?,

                   @SerializedName("description")
                   @Expose
                   var description: String?,

                   @SerializedName("dj")
                   @Expose
                   var dj: String?,

                   @SerializedName("djmail")
                   @Expose
                   var djmail: String?,

                   @SerializedName("genre")
                   @Expose
                   var genre: String?,

                   @SerializedName("image")
                   @Expose
                   var image: String?,

                   @SerializedName("largeimage")
                   @Expose
                   var largeimage: String?,

                   @SerializedName("xlimage")
                   @Expose
                   var xlimage: String?,

                   @SerializedName("twitter")
                   @Expose
                   var twitter: String?,

                   @SerializedName("updated")
                   @Expose
                   var updated: String?,

//                   @SerializedName("playlists")
//                   @Expose
//                   var playlists: List<Playlist>?,

                   @SerializedName("listeners")
                   @Expose
                   var listeners: String?,

                   @SerializedName("lastPlaying")
                   @Expose
                   var lastPlaying: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(dj)
        parcel.writeString(djmail)
        parcel.writeString(genre)
        parcel.writeString(image)
        parcel.writeString(largeimage)
        parcel.writeString(xlimage)
        parcel.writeString(twitter)
        parcel.writeString(updated)
//        parcel.writeTypedList(playlists)
        parcel.writeString(listeners)
        parcel.writeString(lastPlaying)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Channel> {
        override fun createFromParcel(parcel: Parcel): Channel {
            return Channel(parcel)
        }

        override fun newArray(size: Int): Array<Channel?> {
            return arrayOfNulls(size)
        }
    }
}