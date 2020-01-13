/**
 * Config Activity, handles user interaction and obtains data for server setting.
 */
package es.fempa.estebanescobar.chatxd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class ConfigActivity extends AppCompatActivity {
    Switch s;
    private  TextView info;
    private EditText etIP;
    private EditText etPort;
    private EditText etName;
    private Hilos h;
    private Button btnConn;
    private Button btnCancel;
    static final private int ID_MENU_SETTINGS = Menu.FIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        s = findViewById(R.id.switch1);
        info = findViewById(R.id.test);
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        etName = findViewById(R.id.etName);
        btnConn = findViewById(R.id.btnConn);
        btnCancel = findViewById(R.id.btnCancel);
        h = new Hilos(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        int groupId = 0;
        int menuItemId = ID_MENU_SETTINGS;
        int menuItemOrder = menu.NONE;
        String menuItemText = "Settings";
        MenuItem menuItem = menu.add(groupId, menuItemId, menuItemOrder, menuItemText);
        return true;
    }

    public void onClickConnect(View v){
        if(s.isChecked() && validateInputs(true)){
            //Cliente
            btnConn.setEnabled(false);
            h.openClient(Integer.parseInt(etPort.getText().toString()),etIP.getText().toString());
        }else if (validateInputs(false)){
            //Servidor
            h.openServer(Integer.parseInt(etPort.getText().toString()));
        }
    }

    public void onClickCancel(View v){
        try {
            SocketData.getInstance().getServerSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        revertButtons(true);
    }

    public void onClickSwitch(View v){
        if(s.isChecked()){
            //Cliente
            etIP.setEnabled(true);
        }else{
            //Servidor
           etIP.setEnabled(false);
        }
    }

    public boolean validateInputs(boolean type){
        boolean salida = true;

        if(type){
            //Cliente
            if(etIP.getText().toString().equals("") || etPort.getText().toString().equals("") || etName.getText().toString().equals("")){
                info.setText("No se pueden dejar campos vacíos");
                salida = false;
            }
        }else{
            //Servidor
            if(etPort.getText().toString().equals("") || etName.getText().toString().equals("")){

                info.setText("No se pueden dejar campos vacíos");
                salida = false;
            }
        }

        return salida;
    }

    public void revertButtons(boolean revert){
        if (revert){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnCancel.setEnabled(false);
                    btnConn.setEnabled(true);
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnCancel.setEnabled(true);
                    btnConn.setEnabled(false);
                }
            });
        }

    }

    public void changeText(String text){
        final String aux = text;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info.setText(aux);
            }
        });
    }
}
