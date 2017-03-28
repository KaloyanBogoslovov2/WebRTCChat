package com.bogoslovov.kaloyan.webrtcchat;

import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

/**
 * Created by kaloqn on 3/28/17.
 */

public class SDPObserver implements SdpObserver{
    @Override
    public void onCreateSuccess(SessionDescription sessionDescription) {


    }

    @Override
    public void onSetSuccess() {

    }

    @Override
    public void onCreateFailure(String s) {

    }

    @Override
    public void onSetFailure(String s) {

    }
}
