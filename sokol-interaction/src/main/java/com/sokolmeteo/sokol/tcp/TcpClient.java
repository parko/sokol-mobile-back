package com.sokolmeteo.sokol.tcp;

import java.io.IOException;

public interface TcpClient {
    void sendMessage(String login, String message) throws IOException;
}
