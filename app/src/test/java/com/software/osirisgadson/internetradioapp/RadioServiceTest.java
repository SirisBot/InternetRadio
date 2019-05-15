package com.software.osirisgadson.internetradioapp;


import com.software.osirisgadson.internetradioapp.data.model.Channel;
import com.software.osirisgadson.internetradioapp.data.model.Channels;
import com.software.osirisgadson.internetradioapp.data.network.radio.RadioUtil;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Iterator;

import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertTrue;

public class RadioServiceTest {

    //very basic test to ensure that service is retrieving values
    @Test
    public void getRadioChannels() {
        RadioUtil radioUtil = new RadioUtil();
        TestSubscriber<Channels> testSubscriber = radioUtil.getService().getRadioChannels().test();
        testSubscriber.assertNoErrors();
        testSubscriber.assertSubscribed();
        testSubscriber.assertComplete();
        assertTrue(!testSubscriber.values().isEmpty());
    }

    @Test
    public void getFilteredRadioChannels() {

        //very basic test to check if items emitted are filtered according to input
        String filter = "elise";

        RadioUtil radioUtil = new RadioUtil();
        TestSubscriber<Channels> testSubscriber = radioUtil.getService()
                .getRadioChannels()
                .filter(channels -> {
                    Iterator<Channel> channelList = channels.getChannels().iterator();
                    while (channelList.hasNext()) {
                        Channel channel = channelList.next();
                        if (!StringUtils.containsIgnoreCase(channel.getDj(), filter)) {
                            channelList.remove();
                        }
                    }
                    return true;
                }).test();
        testSubscriber.assertNoErrors();
        testSubscriber.assertSubscribed();
        testSubscriber.assertComplete();
        assertTrue(StringUtils.equalsAnyIgnoreCase(filter, testSubscriber.values().get(0).getChannels().get(0).getDj()));
    }

    @Test
    public void getChannels_Offline_hasData() {

    }

    @Test
    public void getChannels_Offline_hasNoData() {

    }

    @Test
    public void getChannelsOnline_hasData_getLocalCache() {

    }

    @Test
    public void getChannelsOnline_hasNoData_getRemoteData() {

    }
}
