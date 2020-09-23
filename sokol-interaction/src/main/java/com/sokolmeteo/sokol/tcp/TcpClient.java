package com.sokolmeteo.sokol.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class TcpClient {
    private final String host;
    private final int port;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String execute(String login, String message) {
        try (Socket socket = new Socket(host, port);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {
            out.write(login.getBytes());
            byte[] response = new byte[1000];
            int length = in.read(response);
            StringTokenizer tokenizer = new StringTokenizer(new String(response, 0, length), "#\r\n");
            if (!tokenizer.nextToken().equals("AL") || !tokenizer.nextToken().equals("1")) {
                return "Не удалось авторизовать устройство";
            }

            out.write(message.getBytes());
            length = socket.getInputStream().read(response);
            tokenizer = new StringTokenizer(new String(response, 0, length), "#\r\n");
            if (!tokenizer.nextToken().startsWith("A") || tokenizer.nextToken().equals("0")) {
                return "Ошибка при отправке BLACK";
            }
            return "OK";
        } catch (IOException e) {
            return "Внутренняя ошибка";
        }
    }
}
