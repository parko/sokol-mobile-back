package com.sokolmeteo.sokol.tcp;

import com.sokolmeteo.sokol.config.ConnectionProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

@Component
public class TcpClient {
    private final Logger logger = LoggerFactory.getLogger(TcpClient.class);

    private final ConnectionProps connectionProps;
    private static final int TIMEOUT_MIN = 3;

    public TcpClient(ConnectionProps connectionProps) {
        this.connectionProps = connectionProps;
    }

    public String execute(String loginMessage, String blackMessage) {
        try (Socket socket = new Socket(connectionProps.getTcpHost(), connectionProps.getTcpPort());
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {
            socket.setSoTimeout(TIMEOUT_MIN * 60_000);
            out.write(loginMessage.getBytes());
            byte[] response = new byte[1000];
            int length = in.read(response);
            StringTokenizer tokenizer = new StringTokenizer(new String(response, 0, length), "#\r\n");
            if (!tokenizer.nextToken().equals("AL") || !tokenizer.nextToken().equals("1"))
                return "Unauthorized device";

            out.write(blackMessage.getBytes());
            length = socket.getInputStream().read(response);
            tokenizer = new StringTokenizer(new String(response, 0, length), "#\r\n");
            if (!tokenizer.nextToken().startsWith("A") || tokenizer.nextToken().equals("0"))
                return "Invalid black message";
            return "OK";
        } catch (Exception e) {
            logger.error("Exception on sending message: " + e);
            return "Internal server error";
        }
    }
}
