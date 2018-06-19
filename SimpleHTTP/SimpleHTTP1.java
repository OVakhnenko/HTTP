package SimpleHTTP;

import Utils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class SimpleHTTP1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            System.out.println("wait for TCP-connection...");
            Socket socket = serverSocket.accept();
            System.out.println("get one!");
            try (InputStream in = socket.getInputStream()) {
                OutputStream ous = socket.getOutputStream();

                byte[] request = HttpUtils.readRequestFully(in);
                System.out.println("-------------------------------");
                System.out.println(new String(request, US_ASCII));
                System.out.println("-------------------------------");

                byte[] responce = new Date().toString().getBytes(US_ASCII);
                ous.write(responce);
            } finally {
                if (socket != null && !socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
            // TODO: socket.close!!
        }
    }
}
