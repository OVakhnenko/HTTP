package SimpleHTTP;

import Utils.HttpHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleHTTP2 {
    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = new ThreadPoolExecutor(
                4, 64,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(256));
        ServerSocket serverSocket = new ServerSocket(80);

        while (true) {
            final Socket socket = serverSocket.accept();
            threadPool.submit(new HttpHandler(socket));
        }
    }
}
