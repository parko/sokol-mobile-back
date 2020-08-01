package com.sokolmeteo.sokol.tcp;

public interface TcpInteraction {
    void sendData(String loginMessage, String message) throws TcpInteractionException;
}
