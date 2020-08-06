package com.sokolmeteo.sokol.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class TcpClientImpl implements TcpClient {
    private final static Logger logger = LoggerFactory.getLogger(TcpClientImpl.class);

    private final String host;
    private final int port;

    public TcpClientImpl(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void sendMessage(String login, String message) throws IOException, TcpClientException {
        try (Socket socket = new Socket(host, port);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {
            out.write(login.getBytes());
            byte[] response = new byte[1000];
            int length = in.read(response);
            StringTokenizer tokenizer = new StringTokenizer(new String(response, 0, length), "#\r\n");
            if (!tokenizer.nextToken().equals("AL") || !tokenizer.nextToken().equals("1")) {
                logger.warn("Error on authorizing: " + login);
                throw new TcpClientException("Error on authorizing", login);
            }

            out.write(message.getBytes());
            length = socket.getInputStream().read(response);
            tokenizer = new StringTokenizer(new String(response, 0, length), "#\r\n");
            if (!tokenizer.nextToken().startsWith("A") || tokenizer.nextToken().equals("0")) {
                logger.warn("Error on sending");
                throw new TcpClientException("Error on sending", message);
            }
        }
    }
}
