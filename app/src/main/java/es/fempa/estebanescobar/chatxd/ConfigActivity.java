/**
 * Config Activity, handles user interaction and obtains data for server setting.
 */
package es.fempa.estebanescobar.chatxd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

public class ConfigActivity extends AppCompatActivity {
    Switch s;
    private  TextView info;
    private EditText etIP;
    private EditText etPort;
    private EditText etName;
    private Hilos h;
    private Button btnConn;
    private Button btnCancel;
    private Toolbar mTopToolbar;

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

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            Intent intent = new Intent(ConfigActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //Connect button event
    public void onClickConnect(View v){
        if(s.isChecked() && validateInputs(true)){
            //Cliente
            btnConn.setEnabled(false);
            CurrentUsers.getInstance().getMe().setName(etName.getText().toString());
            h.openClient(Integer.parseInt(etPort.getText().toString()),etIP.getText().toString());
        }else if (validateInputs(false)){
            //Servidor
            CurrentUsers.getInstance().getMe().setName(etName.getText().toString());
            h.openServer(Integer.parseInt(etPort.getText().toString()));
        }
    }

    //Cancel button event
    public void onClickCancel(View v){
        if (!SocketData.getInstance().isConnected()){
            h.disconnect();
        }
    }

    //Switch event
    public void onClickSwitch(View v){
        if(s.isChecked()){
            //Cliente
            etIP.setEnabled(true);
        }else{
            //Servidor
           etIP.setEnabled(false);
        }
    }

    //Checks if any input is empty
    public boolean validateInputs(boolean type){
        boolean salida = true;

        if(type){
            //Cliente
            if(etIP.getText().toString().equals("") || etPort.getText().toString().equals("") || etName.getText().toString().equals("")){
                info.setText(getResources().getString(R.string.emptyField));
                salida = false;
            }
        }else{
            //Servidor
            if(etPort.getText().toString().equals("") || etName.getText().toString().equals("")){

                info.setText(getResources().getString(R.string.emptyField));
                salida = false;
            }
        }

        return salida;
    }

    //Enables/Disables ui buttons
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

    //Changes bottom text
    public void changeText(String text){
        final String aux = text;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info.setText(aux);
            }
        });
    }

    //Switches to chat activity
    public void SwitchToChatActivity(){
        Intent intent = new Intent(ConfigActivity.this, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, 1);
    }

    //Checks if device is connected to a network
    public boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        h.messageSender("!-_-_-_-_;");
        h.disconnect();
    }
}
