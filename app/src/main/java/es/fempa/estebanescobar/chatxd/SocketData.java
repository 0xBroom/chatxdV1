package es.fempa.estebanescobar.chatxd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class SocketData {

    private static SocketData instance = null;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;
    private String ip;
    private int port;
    private boolean connected;

    private SocketData(){
        serverSocket = null;
        clientSocket = null;
        inputStream = null;
        outputStream = null;
    }

    public void OpenAndWaitServer(int p){
        this.port = p;
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    serverSocket = new ServerSocket(port);
                    ip = getPhoneIP();
                    socket = serverSocket.accept();
                    try {
                        inputStream = new DataInputStream(socket.getInputStream());
                        outputStream = new DataOutputStream(socket.getOutputStream());
                    }catch(Exception e){ e.printStackTrace();}
                    connected = true;
                }catch(Exception e){
                    connected = false;
                }
            }
        };
    }

    private String getPhoneIP(){
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

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
}