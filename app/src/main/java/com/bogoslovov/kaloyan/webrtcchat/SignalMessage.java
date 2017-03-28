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


    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Object getSdp() {
        return sdp;
    }

    public void setSdp(Object sdp) {
        this.sdp = sdp;
    }
}
