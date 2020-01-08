/**
 * Main activity, used to handle the interaction with user and to ask for server's setting.
 *
 *
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
    private static TextView test;
    private EditText etIP;
    private EditText etPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        s = findViewById(R.id.switch1);
        test = findViewById(R.id.test);
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
    }

    public void connect(View v){
        if(s.isChecked()){
            //Client.
            Hilos.openClient(Integer.parseInt(etPort.getText().toString()),etIP.getText().toString());
        }else{
            //Server.
            Hilos.openServer(Integer.parseInt(etPort.getText().toString()));
        }
    }

    public static void changeText(String ip){
        test.setText("Conectado");
    }
}
