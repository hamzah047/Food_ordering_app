package com.example.newchatbot.model;

public class ChatMessage {
    private boolean isMine;
    private String content;

    public ChatMessage(boolean isMine, String content) {
        this.isMine = isMine;
        this.content = content;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}