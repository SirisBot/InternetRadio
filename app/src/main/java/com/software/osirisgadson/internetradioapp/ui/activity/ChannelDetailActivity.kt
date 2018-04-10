package com.software.osirisgadson.internetradioapp.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.software.osirisgadson.internetradioapp.R
import com.software.osirisgadson.internetradioapp.data.model.Channel
import com.software.osirisgadson.internetradioapp.ui.activity.ChannelListActivity.CHANNEL_EXTRA
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_channel_detail.*

class ChannelDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val channel: Channel = intent.extras.getParcelable(CHANNEL_EXTRA)
        Picasso.get().load(channel.xlimage).into(iv_channel_detail_image)
        tv_channel_detail_title.text = channel.title
        tv_channel_detail_dj.text = channel.dj
        tv_channel_detail_dj_email.text = channel.djmail
        tv_channel_detail_listeners.text = channel.listeners //good candidate for livedata implementation
        tv_channel_detail_genre.text = channel.genre
    }
}
