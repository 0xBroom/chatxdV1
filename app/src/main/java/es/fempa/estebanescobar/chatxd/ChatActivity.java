package es.fempa.estebanescobar.chatxd;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        h = new Hilos(this);
        h.messageListener();
        adapter =  new MessagesListAdapter<>("1", null); //Sets the adapter
        inputView = findViewById(R.id.input);
        messagesList = findViewById(R.id.messagesList);
        currentUser = new Author("1", "Me");
        otherUser = new Author("2", "You");
        messagesList.setAdapter(adapter);


        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                adapter.addToStart(new Message(input.toString(), currentUser), true);
                h.messageSender(input.toString());
                return true;
            }
        });
    }

    public void printMessage(String m){
        final String message = m;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addToStart(new Message(message, otherUser), true);
            }
        });
    }




}
