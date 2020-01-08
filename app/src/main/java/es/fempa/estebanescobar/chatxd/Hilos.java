package es.fempa.estebanescobar.chatxd;

import android.app.Activity;
import android.content.pm.ConfigurationInfo;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Hilos {

    private ConfigActivity a;

    public Hilos(ConfigActivity a){
        this.a = a;
    }
    //Create client connection
    public  void openClient(int port, String ip){
        SocketData.getInstance().setIp(ip);
        SocketData.getInstance().setPort(port);

        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    SocketData.getInstance().setSocket(new Socket(SocketData.getInstance().getIp(), SocketData.getInstance().getPort()));
                    SocketData.getInstance().setConnected(true);
                    a.changeText("Connecting...");
                    a.changeText("Connected");
                } catch (IOException e) {
                    e.printStackTrace();
                    a.changeText(e.toString());
                    SocketData.getInstance().setConnected(false);
                }
            }
        };
        t.start();
    }

    //Open server socket
    public  void openServer(int port){
        SocketData.getInstance().setPort(port);
        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();//
                try{
                    Log.e("lul", "entra con ip: "+SocketData.getInstance().getIp());
                    SocketData.getInstance().setServerSocket(new ServerSocket(SocketData.getInstance().getPort())); //Creates server socket
                    SocketData.getInstance().setIp(SocketData.getPhoneIP()); //Grabs phone IP
                    a.changeText("Waiting in: "+SocketData.getInstance().getIp());
                    SocketData.getInstance().setSocket(SocketData.getInstance().getServerSocket().accept()); //Waits for client connection
                    a.changeText("Connected");
                    try {
                        SocketData.getInstance().setInputStream(new DataInputStream(SocketData.getInstance().getSocket().getInputStream())); //Creates data input stream
                        SocketData.getInstance().setOutputStream(new DataOutputStream(SocketData.getInstance().getSocket().getOutputStream())); //Creates data output stream
                    }catch(Exception e){ e.printStackTrace();}
                    SocketData.getInstance().setConnected(true);
                }catch(Exception e){
                    SocketData.getInstance().setConnected(false);
                    a.changeText(e.toString());
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }



}
