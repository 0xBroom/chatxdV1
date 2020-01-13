/**
 * This class handles all Threads that are used in the program.
 * Each method override Run() method on a Thread() object instance.
 *
 */
package es.fempa.estebanescobar.chatxd;
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
                    a.changeText(String.valueOf(R.string.connecting));
                    SocketData.getInstance().setSocket(new Socket(SocketData.getInstance().getIp(), SocketData.getInstance().getPort()));
                    SocketData.getInstance().setConnected(true);
                    a.changeText(String.valueOf(R.string.connected));
                }catch(UnknownHostException u){
                    a.changeText(String.valueOf(R.string.unknownHostException));
                }catch(BindException b){
                    a.changeText(String.valueOf(R.string.bindException));
                }catch(SocketException s){
                    a.changeText(String.valueOf(R.string.welcome));  //Aqui va el texto por defecto
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
                    a.changeText(String.valueOf(R.string.waitingIn)+SocketData.getInstance().getIp());
                    a.revertButtons(false);
                    SocketData.getInstance().setSocket(SocketData.getInstance().getServerSocket().accept()); //Waits for client connection
                    a.changeText(String.valueOf(R.string.connected));
                    try {
                        SocketData.getInstance().setInputStream(new DataInputStream(SocketData.getInstance().getSocket().getInputStream())); //Creates data input stream
                        SocketData.getInstance().setOutputStream(new DataOutputStream(SocketData.getInstance().getSocket().getOutputStream())); //Creates data output stream
                    }catch(Exception e){ e.printStackTrace();}
                    SocketData.getInstance().setConnected(true);

                }catch(UnknownHostException u){
                    a.changeText(String.valueOf(R.string.unknownHostException));
                }catch(BindException b){
                    a.changeText(String.valueOf(R.string.bindException));
                }catch(SocketException s){
                    a.changeText(String.valueOf(R.string.welcome));  //Aqui va el texto por defecto
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



}
