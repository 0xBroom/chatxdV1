package es.fempa.estebanescobar.chatxd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;


public class ChatActivity extends AppCompatActivity {
    public static int max_userid; //Para tener controlado el número de ids creados.
    public static int max_messid;
    MessagesList messagesList;
    MessageInput inputView;
    MessagesListAdapter<Message> adapter;
    Author currentUser;
    Author otherUser;
    Hilos h;
    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        h = new Hilos(this);
        h.messageListener();
        adapter =  new MessagesListAdapter<>("1", null); //Sets the adapter
        inputView = findViewById(R.id.input);
        messagesList = findViewById(R.id.messagesList);
        h.messageSender("_-_-_-_-_;"+CurrentUsers.getInstance().getMe().getName());
        messagesList.setAdapter(adapter);


        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                adapter.addToStart(new Message(input.toString(), CurrentUsers.getInstance().getMe()), true);
                h.messageSender(input.toString());
                return true;
            }
        });

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setIcon(R.drawable.userpic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item's clicks.
        Intent intent;
        boolean returnar = false;
        switch (item.getItemId())
        {
            case R.id.action_info:
                intent = new Intent(ChatActivity.this, AboutActivity.class);
                startActivity(intent);
                returnar = true;
                break;
            case android.R.id.home:
                //SALIDA DE LA ACTIVIDAD.
                finish();
                returnar = true;
                break;
        }

        if (returnar){
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    public void printMessage(String m){
        final String message = m;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addToStart(new Message(message, CurrentUsers.getInstance().getOther()), true);
            }
        });
    }




}
