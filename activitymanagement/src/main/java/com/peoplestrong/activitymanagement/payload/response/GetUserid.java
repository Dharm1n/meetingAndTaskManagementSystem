package com.peoplestrong.activitymanagement.payload.response;

public class GetUserid {
    private Long userid;

    public GetUserid() {
    }

    public GetUserid(Long userid) {
        this.userid = userid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
