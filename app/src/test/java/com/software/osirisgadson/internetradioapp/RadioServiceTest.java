package com.software.osirisgadson.internetradioapp;


import com.software.osirisgadson.internetradioapp.data.network.radio.RadioUtil;

import org.junit.Test;

import io.reactivex.observers.TestObserver;

public class RadioServiceTest {

    @Test
    public void getRadioChannels() {
        RadioUtil radioUtil = new RadioUtil();
        TestObserver testObserver = radioUtil.getService().getRadioChannels().test();
        testObserver.assertNoErrors();
        testObserver.assertSubscribed();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }
}
