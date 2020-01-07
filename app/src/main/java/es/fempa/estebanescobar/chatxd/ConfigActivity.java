package es.fempa.estebanescobar.chatxd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class ConfigActivity extends AppCompatActivity {
    Switch s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        s = (Switch) findViewById(R.id.switch1);
    }

    public void connect(View v){
        if(s.isChecked()){
            //Cliente
        }else{
            //Servidor
        }
    }
}
