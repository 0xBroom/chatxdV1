package es.fempa.estebanescobar.chatxd;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class SocketData {

    private static SocketData instance = null;

    private ServerSocket serverSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;
    private String ip;
    private int port;
    private boolean connected;

    private SocketData(){
        serverSocket = null;
        inputStream = null;
        outputStream = null;
    }


    public static String getPhoneIP(){
        String phoneIP = "";
        try
        {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements())
            {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
                while (enumInetAddress.hasMoreElements())
                {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress())
                    {
                        phoneIP = inetAddress.getHostAddress();
                    }

                }
            }
        } catch (SocketException e)
        {
            e.printStackTrace();
            phoneIP = "ERROR";
        }
        return phoneIP;
    }

    //Get Instance
    public static SocketData getInstance(){
        if (instance == null){
            instance = new SocketData();
        }
        return instance;
    }

    //Getters & Setters
    public static void setInstance(SocketData instance) {
        SocketData.instance = instance;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public DataInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

}
