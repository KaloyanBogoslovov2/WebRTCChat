package com.bogoslovov.kaloyan.webrtcchat;

/**
 * Created by kaloqn on 3/28/17.
 */

public class PeerOptions {

    private String id;
    private String calleeId;
    private String roomUUID;
    private boolean caller;

    public PeerOptions(String id, String calleeId, String roomUUID, boolean caller) {
        this.id = id;
        this.calleeId = calleeId;
        this.roomUUID = roomUUID;
        this.caller = caller;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCalleeId() {
        return calleeId;
    }

    public void setCalleeId(String calleeId) {
        this.calleeId = calleeId;
    }

    public String getRoomUUID() {
        return roomUUID;
    }

    public void setRoomUUID(String roomUUID) {
        this.roomUUID = roomUUID;
    }

    public boolean isCaller() {
        return caller;
    }

    public void setCaller(boolean caller) {
        this.caller = caller;
    }


}