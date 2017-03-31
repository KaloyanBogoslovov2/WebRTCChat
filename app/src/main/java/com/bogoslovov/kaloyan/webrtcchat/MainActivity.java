package com.bogoslovov.kaloyan.webrtcchat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    private ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initButtons();
    }

    private void initButtons(){
        Button client1 = (Button)findViewById(R.id.client_1);
        Button client2 = (Button)findViewById(R.id.client_2);

        client1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSomething();
            }

        });
        client2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSomethingElse();
            }
        });
    }

    private void doSomething(){


        //////////////////////////////////sockets////////////////////////////////////////////////
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                WebSocketFactory factory = new WebSocketFactory();
                final WebSocket webSocket = getWebSocket(factory);

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

                PeerObserver observer = new PeerObserver(webSocket);

                final PeerConnection peerConnection = peerConnectionFactory.createPeerConnection(
                        iceServers,
                        mediaConstraints,
                        observer);

                SdpObserver sdpObserver = new SdpObserver() {
                    @Override
                    public void onCreateSuccess(SessionDescription sessionDescription) {
                        Log.i("information","CREATE SUCCESSthtyhtyh OFFER");
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
                webSocket.addListener(new SocketAdapter(peerConnection,sdpObserver,mediaConstraints));
                SignalMessage message = new SignalMessage(SignalMessage.MsgType.OFFER, "sender", "51", "chico Slavcho", null);
                connect(message, webSocket);

                peerConnection.createOffer(sdpObserver,mediaConstraints);

                return null;
            }
        };
        task.execute();



    }

    private  void doSomethingElse(){

        //////////////////////////////////sockets////////////////////////////////////////////////
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                WebSocketFactory factory = new WebSocketFactory();
                final WebSocket webSocket = getWebSocket(factory);

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

                PeerObserver observer = new PeerObserver(webSocket);

                final PeerConnection peerConnection = peerConnectionFactory.createPeerConnection(
                        iceServers,
                        mediaConstraints,
                        observer);

                SdpObserver sdpObserver2 = new SdpObserver() {
                    @Override
                    public void onCreateSuccess(SessionDescription sessionDescription) {
                        Log.i("information","CREATE SUCCESSthtyhtyh ANSWER");
                        peerConnection.setLocalDescription(this,sessionDescription);
                        System.out.println("sessionDescription:"+sessionDescription.description);
                        SignalMessage answerMessage = new SignalMessage(SignalMessage.MsgType.ANSWER,"sender", "51", "chico Slavcho", sessionDescription);
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


                webSocket.addListener(new SocketAdapter(peerConnection,sdpObserver2,mediaConstraints));
                SignalMessage message = new SignalMessage(SignalMessage.MsgType.GET_OFFER, "sender", "51", "chico Slavcho", null);
                connect(message,webSocket);

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

    private void connect(SignalMessage message, WebSocket webSocket){
        try {
            webSocket.connect();
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
