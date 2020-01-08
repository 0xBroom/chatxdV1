/**
This class handle all Threads needed in the app,
each thread is created on a method by overriting Run() method on the Thread() object instance.
 */

package es.fempa.estebanescobar.chatxd;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Hilos {


    //Create client connection
    public static void openClient(int port, String ip){
        SocketData.getInstance().setIp(ip);
        SocketData.getInstance().setPort(port);

        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //Log the client on the server
                    Log.e("lul", "entra con ip: "+SocketData.getInstance().getIp());
                    SocketData.getInstance().setSocket(new Socket(SocketData.getInstance().getIp(), SocketData.getInstance().getPort()));
                    SocketData.getInstance().setConnected(true);
                    ConfigActivity.changeText("Waiting...");
                } catch (IOException e) {
                    e.printStackTrace();
                    SocketData.getInstance().setConnected(false);
                }
            }
        };
        t.start();
    }

    //Create server
    public static void openServer(int port){
        SocketData.getInstance().setPort(port);
        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    SocketData.getInstance().setServerSocket(new ServerSocket(SocketData.getInstance().getPort())); //Creates server socket
                    SocketData.getInstance().setIp(SocketData.getPhoneIP()); //Grabs phone IP
                    SocketData.getInstance().setSocket(SocketData.getInstance().getServerSocket().accept()); //Waits for client connection
                    try {
                        SocketData.getInstance().setInputStream(new DataInputStream(SocketData.getInstance().getSocket().getInputStream())); //Creates data input stream
                        SocketData.getInstance().setOutputStream(new DataOutputStream(SocketData.getInstance().getSocket().getOutputStream())); //Creates data output stream
                    }catch(Exception e){ e.printStackTrace();}
                    SocketData.getInstance().setConnected(true);
                    ConfigActivity.changeText(SocketData.getInstance().getIp());
                }catch(Exception e){
                    SocketData.getInstance().setConnected(true);
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


}
