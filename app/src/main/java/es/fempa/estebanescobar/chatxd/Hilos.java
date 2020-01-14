/**
 * This class handles all Threads that are used in the program.
 * Each method override Run() method on a Thread() object instance.
 *
 */
package es.fempa.estebanescobar.chatxd;
import android.content.res.Resources;
import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Hilos {

    private ConfigActivity a;
    private ChatActivity c;
    private boolean reading;

    public Hilos(ConfigActivity a){
        this.a = a;
    }

    public Hilos (ChatActivity c){
        this.c  = c;
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
                    a.changeText(a.getResources().getString(R.string.connecting));
                    SocketData.getInstance().setSocket(new Socket(SocketData.getInstance().getIp(), SocketData.getInstance().getPort())); //Creates socket
                    SocketData.getInstance().setConnected(true);
                    a.SwitchToChatActivity();
                    try {
                        SocketData.getInstance().setInputStream(new DataInputStream(SocketData.getInstance().getSocket().getInputStream())); //Creates data input stream
                        SocketData.getInstance().setOutputStream(new DataOutputStream(SocketData.getInstance().getSocket().getOutputStream())); //Creates data output stream
                    }catch(Exception e){ e.printStackTrace();}
                    a.changeText(a.getResources().getString(R.string.connected));
                }catch(UnknownHostException u){
                    a.changeText(a.getResources().getString(R.string.unknownHostException));
                }catch(BindException b){
                    a.changeText(a.getResources().getString(R.string.bindException));
                }catch(SocketException s){
                    a.changeText(a.getResources().getString(R.string.welcome));
                }catch(Exception e){
                    SocketData.getInstance().setConnected(false);
                    a.changeText(e.toString());
                    e.printStackTrace();
                }finally {
                    a.revertButtons(true);
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
                    SocketData.getInstance().setServerSocket(new ServerSocket(SocketData.getInstance().getPort())); //Creates server socket
                    SocketData.getInstance().setIp(SocketData.getPhoneIP()); //Grabs phone IP
                    a.changeText(a.getResources().getString(R.string.waitingIn)+SocketData.getInstance().getIp());
                    a.revertButtons(false);
                    SocketData.getInstance().setSocket(SocketData.getInstance().getServerSocket().accept()); //Waits for client connection
                    a.changeText(a.getResources().getString(R.string.connected));
                    a.SwitchToChatActivity();
                    try {
                        SocketData.getInstance().setInputStream(new DataInputStream(SocketData.getInstance().getSocket().getInputStream())); //Creates data input stream
                        SocketData.getInstance().setOutputStream(new DataOutputStream(SocketData.getInstance().getSocket().getOutputStream())); //Creates data output stream
                    }catch(Exception e){ e.printStackTrace();}
                    SocketData.getInstance().setConnected(true);

                }catch(UnknownHostException u){
                    a.changeText(a.getResources().getString(R.string.unknownHostException));
                }catch(BindException b){
                    a.changeText(a.getResources().getString(R.string.bindException));
                }catch(SocketException s){
                    a.changeText(a.getResources().getString(R.string.welcome));  //Aqui va el texto por defecto
                }catch(Exception e){
                    SocketData.getInstance().setConnected(false);
                    a.changeText(e.toString());
                    e.printStackTrace();
                }finally {
                    a.revertButtons(true);
                }
            }
        };
        t.start();
    }

    //Message listener
    public void messageListener(){
        reading = true;

        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                reading = true;

                while(reading){
                    String line = "";
                    line = getMessage();
                    if(line!="" && line.length()!=0) {
                        c.printMessage(line);
                    }
                }
            }
        };
        t.start();

    }

    //Gets message from inputstream
    private String getMessage()
    {
        String message="";

        try {
            message=SocketData.getInstance().getInputStream().readUTF();
        }catch(Exception e)
        {
            e.printStackTrace();
            reading=false;
        }
        return message;
    }

    public void messageSender(String m){
        final String message = m;
        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    SocketData.getInstance().getOutputStream().writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

}
