package com.bogoslovov.kaloyan.webrtcchat;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketListener;
import com.neovisionaries.ws.client.WebSocketState;

import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.PeerConnection;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

import java.util.List;
import java.util.Map;

/**
 * Created by kaloqn on 3/28/17.
 */

public class SocketAdapter implements WebSocketListener {
    private PeerConnection peerConnection;
    private SdpObserver sdpObserver;
    private MediaConstraints mediaConstraints;

    public SocketAdapter(PeerConnection peer, SdpObserver observer, MediaConstraints constraints){
        peerConnection = peer;
        sdpObserver = observer;
        mediaConstraints = constraints;
    }
    @Override
    public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {

    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {

    }

    @Override
    public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {

    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {

    }

    @Override
    public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SignalMessage msg = mapper.readValue(text, SignalMessage.class);
        switch (msg.getType()) {
            case OFFER: {
                JsonNode node = mapper.valueToTree(msg.getSdp());
                String sdpPayload = node.get("sdp").asText();
                SessionDescription sessionDescription = new SessionDescription(SessionDescription.Type.OFFER,sdpPayload);
                peerConnection.setRemoteDescription(sdpObserver,sessionDescription);
                peerConnection.createAnswer(sdpObserver,mediaConstraints);
                Log.i("information",msg.getRecipient());
                break;
            }
            case ANSWER: {
                SessionDescription sessionDescription = new SessionDescription(SessionDescription.Type.ANSWER,mapper.writeValueAsString(msg.getSdp()));
                peerConnection.setRemoteDescription(sdpObserver,sessionDescription);
                break;
            }
            case ICE: {
                JsonNode node = mapper.valueToTree(msg.getSdp());
                IceCandidate iceCandidate =  new IceCandidate(node.get("sdpMid").asText(),node.get("sdpMLineIndex").asInt(),
                        node.get("candidate").asText());
                peerConnection.addIceCandidate(iceCandidate);
                break;
            }
            default:
                break;

        }

        System.out.println("The message is: "+text);
    }

    @Override
    public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {

    }

    @Override
    public void onSendingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onFrameUnsent(WebSocket websocket, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {

    }

    @Override
    public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {

    }

    @Override
    public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {

    }

    @Override
    public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {

    }

    @Override
    public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {

    }

    @Override
    public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {

    }

    @Override
    public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {

    }

    @Override
    public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {

    }
}
