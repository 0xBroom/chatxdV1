package es.fempa.estebanescobar.chatxd;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;


public class MainActivity extends AppCompatActivity {
    public static int max_userid; //Para tener controlado el número de ids creados.
    public static int max_messid;
    MessagesList messagesList;
    MessageInput inputView;
    MessagesListAdapter<Message> adapter;
    Author currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter =  new MessagesListAdapter<>("1", null);
        inputView = findViewById(R.id.input);
        messagesList = findViewById(R.id.messagesList);
        currentUser = new Author("1", "test");
        messagesList.setAdapter(adapter);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                adapter.addToStart(new Message(input.toString(), currentUser), true);
                return true;
            }
        });
    }


}
