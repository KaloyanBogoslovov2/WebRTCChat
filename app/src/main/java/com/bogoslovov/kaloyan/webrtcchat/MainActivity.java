package com.bogoslovov.kaloyan.webrtcchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.webrtc.MediaConstraints;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doSomething();
    }

    private void doSomething(){
        PeerConnectionFactory.initializeAndroidGlobals(getApplicationContext(), true, true,true, null);
        PeerConnectionFactory peerConnectionFactory = new PeerConnectionFactory();

        MediaConstraints mediaConstraints = new MediaConstraints();
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio","false"));
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo","false"));
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement","true"));

        List<PeerConnection.IceServer> iceServers = new ArrayList<>();
        iceServers.add(new PeerConnection.IceServer(Constants.STUN_SERVER_1));
        iceServers.add(new PeerConnection.IceServer(Constants.STUN_SERVER_2));
        iceServers.add(new PeerConnection.IceServer(Constants.STUN_SERVER_3));
        iceServers.add(new PeerConnection.IceServer(Constants.STUN_SERVER_4));
        iceServers.add(new PeerConnection.IceServer(Constants.STUN_SERVER_5));

        PeerObserver observer = new PeerObserver();

        PeerConnection peerConnection = peerConnectionFactory.createPeerConnection(
                iceServers,
                mediaConstraints,
                observer);


    }
}
