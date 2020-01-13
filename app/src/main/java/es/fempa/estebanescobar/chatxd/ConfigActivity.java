/**
 * Main Activity, handles user interaction and obtains data for server setting.
 */
package es.fempa.estebanescobar.chatxd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class ConfigActivity extends AppCompatActivity {
    Switch s;
    private  TextView info;
    private EditText etIP;
    private EditText etPort;
    private Hilos h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        s = findViewById(R.id.switch1);
        info = findViewById(R.id.test);
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        h = new Hilos(this);
    }

    public void onClickConnect(View v){
        if(s.isChecked()){
            //Cliente
            h.openClient(Integer.parseInt(etPort.getText().toString()),etIP.getText().toString());
        }else{
            //Servidor
            h.openServer(Integer.parseInt(etPort.getText().toString()));
        }
    }

    public void onClickCancel(View v){

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

    public boolean validateInputs(){
        boolean salida = true;

        return salida;
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
