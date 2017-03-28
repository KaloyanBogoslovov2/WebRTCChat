package com.bogoslovov.kaloyan.webrtcchat;

/**
 * Created by kaloqn on 3/28/17.
 */

public class SignalMessage {

    private MsgType type;
    private String sender;
    private String roomId;
    private String recipient;
    private Object sdp;

    public SignalMessage(MsgType type, String sender, String roomId, String recipient, Object sdp) {
        this.type = type;
        this.sender = sender;
        this.roomId = roomId;
        this.recipient = recipient;
        this.sdp = sdp;
    }
    public SignalMessage() {

    }

    /**
     * Msg Type
     */
    public enum MsgType {
        ICE,OFFER,ANSWER,PING,GET_OFFER
    }
}
