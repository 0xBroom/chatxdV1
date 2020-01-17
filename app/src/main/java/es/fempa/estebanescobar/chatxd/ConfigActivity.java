/**
 * Config Activity, handles user interaction and obtains data for server setting.
 */
package es.fempa.estebanescobar.chatxd;

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
       /**
        * Inflate the menu; this adds items to the action bar if it is present.
        */
       getMenuInflater().inflate(R.menu.menu_main, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * Handle action bar item's clicks.
         */
        Intent intent;
        boolean ButtonClicked = false;

        switch (item.getItemId())
        {
            case R.id.action_info: //Info button clicked.
                intent = new Intent(ConfigActivity.this, AboutActivity.class);
                startActivity(intent);
                ButtonClicked = true;
                break;
            default:
                    break;
        }

        return ButtonClicked || super.onOptionsItemSelected(item);
    }


    public void onClickConnect(View v){
        /**
         * Connect button event.
         * Handler to open communications socket.
         */
        if(s.isChecked() && validateInputs(true)){
            //Client
            btnConn.setEnabled(false);
            CurrentUsers.getInstance().getMe().setName(etName.getText().toString());
            h.openClient(Integer.parseInt(etPort.getText().toString()),etIP.getText().toString());
        }else if (validateInputs(false)){
            //Server
            CurrentUsers.getInstance().getMe().setName(etName.getText().toString());
            h.openServer(Integer.parseInt(etPort.getText().toString()));
        }
    }

    public void onClickCancel(View v){
        /**
         * Cancel button event.
         */
        if (!SocketData.getInstance().isConnected()){
            h.disconnect();
        }
    }

    public void onClickSwitch(View v){
        /**
         * Switch event
         */
        if(s.isChecked()){
            //Client
            etIP.setEnabled(true);
        }else{
            //Server
           etIP.setEnabled(false);
        }
    }

    public boolean validateInputs(boolean type){
        /**
         * Checks if any input is empty
         */
        boolean salida = true;

        if(type ){
            //Client
            if(etIP.getText().toString().equals("") || etPort.getText().toString().equals("") || etName.getText().toString().equals("")){
                info.setText(getResources().getString(R.string.emptyField));
                salida = false;
            }
        }else{
            //Server
            if(etPort.getText().toString().equals("") || etName.getText().toString().equals("")){
                info.setText(getResources().getString(R.string.emptyField));
                salida = false;
            }
        }

        return salida;
    }

    public void revertButtons(boolean revert){
        /**
         * Enables/Disables ui buttons
         */
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
        /**
         * Changes bottom text
         */
        final String aux = text;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info.setText(aux);
            }
        });
    }

    public void SwitchToChatActivity(){
        /**
         * Switches to chat activity
         */
        Intent intent = new Intent(ConfigActivity.this, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, 1);
    }

    public boolean isNetworkConnected(){
        /**
         * Checks if device is connected to a network
         */
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * When an user disconnects a message is sent to communicate it to the receptor.
         */
        super.onActivityResult(requestCode, resultCode, data);
        h.messageSender("!-_-_-_-_;"); //Special Message.
        h.disconnect();
    }
}
