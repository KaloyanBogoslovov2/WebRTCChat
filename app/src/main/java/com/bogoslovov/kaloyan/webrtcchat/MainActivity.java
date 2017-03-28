package com.bogoslovov.kaloyan.webrtcchat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.webrtc.MediaConstraints;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;

import java.io.IOException;
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


        //////////////////////////////////sockets////////////////////////////////////////////////
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                WebSocketFactory factory = new WebSocketFactory();
                WebSocket webSocket = getWebSocket(factory);
                webSocket.addListener(new SocketAdapter());
                connect(webSocket);




                ///////////////////////////////////webrtc///////////////////////////////////////////
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


                return null;
            }
        };
        task.execute();

//        peerConnection.createOffer();
//
//        peerConnection.c
//        peerConnection.setLocalDescription();

    }

    private WebSocket getWebSocket(WebSocketFactory factory){
        WebSocket webSocket = null;
        try {
            webSocket = factory.createSocket(Constants.LOCAL_HOST_ENDPOINT);
            webSocket.setPingInterval(60*1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webSocket;
    }

    private void connect(WebSocket webSocket){
        try {
            webSocket.connect();
            SignalMessage message = new SignalMessage(SignalMessage.MsgType.OFFER, "sender", "roomId", "recipient", null);
            ObjectMapper mapper = new ObjectMapper();

            webSocket.sendText(mapper.writeValueAsString(message));

        }catch (JsonProcessingException e) {
                Log.e("error","JsonProcessingException");
                e.printStackTrace();
        }catch (WebSocketException e) {
            e.printStackTrace();
            Log.e("error","WebSocketException");
        }
    }
}
