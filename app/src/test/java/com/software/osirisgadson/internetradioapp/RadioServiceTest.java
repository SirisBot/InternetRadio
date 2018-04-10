package com.software.osirisgadson.internetradioapp;


import com.software.osirisgadson.internetradioapp.data.model.Channel;
import com.software.osirisgadson.internetradioapp.data.model.Channels;
import com.software.osirisgadson.internetradioapp.data.network.radio.RadioUtil;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertTrue;

import io.reactivex.observers.TestObserver;

public class RadioServiceTest {

    //very basic test to ensure that service is retrieving values
    @Test
    public void getRadioChannels() {
        RadioUtil radioUtil = new RadioUtil();
        TestObserver<Channels> testObserver = radioUtil.getService().getRadioChannels().test();
        testObserver.assertNoErrors();
        testObserver.assertSubscribed();
        testObserver.assertComplete();
        assertTrue(!testObserver.values().isEmpty());
    }

    @Test
    public void getFilteredRadioChannels() {

        //very basic test to check if items emitted are filtered according to input
        String filter = "elise";

        RadioUtil radioUtil = new RadioUtil();
        TestObserver<Channels> testObserver = radioUtil.getService()
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
        testObserver.assertNoErrors();
        testObserver.assertSubscribed();
        testObserver.assertComplete();
        assertTrue(StringUtils.equalsAnyIgnoreCase(filter, testObserver.values().get(0).getChannels().get(0).getDj()));
    }
}
