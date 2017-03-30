package com.bogoslovov.kaloyan.webrtcchat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;

import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;

/**
 * Created by kaloyan on 3/27/17.
 */

public class PeerObserver implements PeerConnection.Observer{

    private WebSocket webSocket;

    public PeerObserver(WebSocket socket){
        webSocket = socket;
    }
    @Override
    public void onSignalingChange(PeerConnection.SignalingState signalingState) {

    }

    @Override
    public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {

    }

    @Override
    public void onIceConnectionReceivingChange(boolean b) {

    }

    @Override
    public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {

    }

    @Override
    public void onIceCandidate(IceCandidate iceCandidate) {
        System.out.println("ICE on onIceCandidate");
        SignalMessage iceMessage = new SignalMessage(SignalMessage.MsgType.ICE, "sender", "51", "chico Slavcho", iceCandidate);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String msgToSend = mapper.writeValueAsString(iceMessage);
            webSocket.sendText(msgToSend);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAddStream(MediaStream mediaStream) {

    }

    @Override
    public void onRemoveStream(MediaStream mediaStream) {

    }

    @Override
    public void onDataChannel(DataChannel dataChannel) {

    }

    @Override
    public void onRenegotiationNeeded() {

    }
}
