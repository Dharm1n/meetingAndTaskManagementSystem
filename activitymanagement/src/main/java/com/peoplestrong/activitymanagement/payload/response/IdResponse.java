package com.peoplestrong.activitymanagement.payload.response;

public class IdResponse {
    Long id;

    public IdResponse(Long id) {
        this.id = id;
    }

    public IdResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
