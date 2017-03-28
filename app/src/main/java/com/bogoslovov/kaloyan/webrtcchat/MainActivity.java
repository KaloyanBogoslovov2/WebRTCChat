package com.bogoslovov.kaloyan.webrtcchat;

import android.app.Activity;
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
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PeerConnection peerConnection;
    private WebSocket webSocket;
    private ObjectMapper mapper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doSomething(this);
    }

    private void doSomething(final Activity activity){


        //////////////////////////////////sockets////////////////////////////////////////////////
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                WebSocketFactory factory = new WebSocketFactory();
                webSocket = getWebSocket(factory);
                webSocket.addListener(new SocketAdapter());
                connect();




                ///////////////////////////////////webRTC//////////////////////////////////////////
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

                peerConnection = peerConnectionFactory.createPeerConnection(
                        iceServers,
                        mediaConstraints,
                        observer);

                SdpObserver sdpObserver = new SdpObserver() {
                    @Override
                    public void onCreateSuccess(SessionDescription sessionDescription) {
                        Log.i("information","CREATE SUCCESSthtyhtyh");
                        peerConnection.setLocalDescription(this,sessionDescription);
                        SignalMessage answerMessage = new SignalMessage(SignalMessage.MsgType.OFFER,"sender", "51", "chico Slavcho", sessionDescription);
                        try {
                            webSocket.sendText(mapper.writeValueAsString(answerMessage));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            Log.e("error","JsonProcessingException");
                        }
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
                };

                peerConnection.createOffer(sdpObserver,mediaConstraints);

                return null;
            }
        };
        task.execute();



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

    private void connect(){
        try {
            webSocket.connect();
            SignalMessage message = new SignalMessage(SignalMessage.MsgType.OFFER, "sender", "51", "chico Slavcho", null);
            mapper = new ObjectMapper();

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
