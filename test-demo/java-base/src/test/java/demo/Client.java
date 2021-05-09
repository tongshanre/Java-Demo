package demo;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Client {

    public Client() {

    }

    public static void main(String[] args) throws IOException {

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
        }

    }
}
