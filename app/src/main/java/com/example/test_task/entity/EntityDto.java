package com.example.test_task.entity;

public class EntityDto {
    private String  id;
    private String type;
    private String message_or_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageOrUrl() {
        return message_or_url;
    }

    public void setMessageOrUrl(String message_or_url) {
        this.message_or_url = message_or_url;
    }
}
