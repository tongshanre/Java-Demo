package demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                ServerSocket ss = new ServerSocket(8080);
                Socket socket = ss.accept();
                Thread.sleep(1000 * 10);
                System.out.println(String.format("isclosed:%b, isConnected:%b", socket.isClosed(), socket.isConnected()));
                socket.getOutputStream().write("Hello".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        ClassLoader classLoader = new ClassLoader() {

            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                getSystemClassLoader();
                return this.defineClass(name, new byte[10], 0, 10);
            }
        };
    }
}
